package com.yuan.my_project.mytest.redis.distributelock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Random;

/**
 * 
 * @author yuanjuntao
 * @Date 2017-07-05
 */
public class RedisLock {
	private static Logger log = LoggerFactory.getLogger(RedisLock.class);
	private RedisTemplate<Object, Object> redisTemplate;
	private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLTS = 1000;
	/**
	 * lock key path
	 */
	private String lockKey;
	//锁超时时间，防止线程获取锁后，无限制的等待
	private int expireMsecs = 60 * 1000;
	//锁等待时间，防止线程饥饿
	private int timeoutMsecs = 10 * 1000;
	private volatile boolean locked = false;
	
	public RedisLock(RedisTemplate redisTemplate, String lockKey){
		this.redisTemplate = redisTemplate;
		this.lockKey = lockKey;
	}
	
	public RedisLock(RedisTemplate redisTemplate, String lockKey, int timeoutMsecs){
		this(redisTemplate,lockKey);
		this.timeoutMsecs = timeoutMsecs;
	}
	
	public RedisLock(RedisTemplate redisTemplate, String lockKey, int timeoutMsecs, int expireMsecs){
		this(redisTemplate,lockKey,timeoutMsecs);
		this.expireMsecs = expireMsecs;
	}
	
	public String getLockKey(){
		return lockKey;
	}
	
	public String get(final String key){
		Object obj = null;
		try{
			obj = redisTemplate.execute(new RedisCallback<Object>() {
	
				public Object doInRedis(RedisConnection conn) throws DataAccessException {
					// TODO Auto-generated method stub
					StringRedisSerializer serializer = new StringRedisSerializer();
					byte[] data = conn.get(serializer.serialize(key));
					//byte[] data = conn.get(key);
					conn.close();
					
					return data == null ? null : serializer.deserialize(data);
				}
			});
		}catch(Exception e){
			log.error("get redis error, key : {} ", key);
			log.error("get redis error, cause : {} ", e.getMessage());
			System.out.println("get redis error, cause :  " + e.getMessage());
		}
		
		return obj != null ? obj.toString() : null;
	}
	
	private boolean setNX(final String key, final String value){
		Object obj = null;
		
		try{
			obj = redisTemplate.execute(new RedisCallback<Object>() {

				@Override
				public Object doInRedis(RedisConnection conn) throws DataAccessException {
					// TODO Auto-generated method stub
					StringRedisSerializer serializer = new StringRedisSerializer();
					System.out.println("setNX---serializer.serialize(key) : "+serializer.serialize(key));
					boolean success = conn.setNX(serializer.serialize(key), serializer.serialize(value));
					conn.close();
					return success;
				}
			});
		}catch(Exception e){
			System.out.println("setNX redis error , cause : " + e.getMessage());
			log.error("setNX redis error , key : {}", key);
		}
		
		return obj != null ? (Boolean)obj : false;
	}
	
	private String getSet(final String key, final String value){
		Object obj = null;
		try{
			obj = redisTemplate.execute(new RedisCallback<Object>() {

				@Override
				public Object doInRedis(RedisConnection conn) throws DataAccessException {
					// TODO Auto-generated method stub
					StringRedisSerializer serializer = new StringRedisSerializer();
					System.out.println("getSet---serializer.serialize(key) : "+serializer.serialize(key));
					byte[] oldValue = conn.getSet(serializer.serialize(key), serializer.serialize(value));
					conn.close();
					return oldValue != null ? serializer.deserialize(oldValue) : null;
				}
			});
		}catch(Exception e){
			log.error("getSet redis error, key : {}" , key);
		}
		
		return obj != null ? (String)obj : null;
	}
	
	/**
	 * 获取锁
	 * 
	 * 实现思路：
	 * 主要使用了redis的setNX（if not exists）、getSet命令，缓存了锁。
	 * redis缓存的key就是所有进程共享的锁，value是锁的到期时间（注意：这里把过期时间放在value里，
	 * 而没有使用redis的expire命令设置过期时间，因为使用expire时，当redis宕机时 会发生死锁情况）。
	 * 
	 * 执行过程：
	 * 1.通过setNX尝试设置某个key（锁）的值，返回1 则成功（当前没有这个锁）获得锁，
	 * 	返回0 则获取失败。如果获取失败，可以设置再次尝试获取锁的次数。
	 * 2.锁已经存在则获取锁的当期时间oldTime跟当前时间比较，如果未超时则返回失败；
	 * 否则getSet更新超时时间并获取更新前的值curTime，
	 * 如果curTime=oldTime则获取锁，否则获取失败
	 * @return
	 * @throws InterruptedException 
	 */
	public boolean lock() throws InterruptedException{
		int timeout = timeoutMsecs;
		while(timeout > 0){
			long expires = System.currentTimeMillis() + expireMsecs + 1;
			if(this.setNX(lockKey, ""+expires)){
				locked = true;
				return true;
			}
			String oldTime = this.get(lockKey);
			//锁超时
			if(oldTime != null && Long.parseLong(oldTime) < System.currentTimeMillis()){
				String curTime = this.getSet(lockKey, expires+"");
				if(curTime != null && curTime.equals(oldTime)){
					locked = true;
					return true;
				}
			}
			
			timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLTS;
			System.out.println("没有获取锁，重试--lockKey:"+lockKey);
			//延迟随机100内的时间毫秒数，防止饥饿进程的出现
			Thread.sleep(new Random().nextInt(100));
		}
		
		return false;
	}
	
	public void unlock(){
		if(locked){
			StringRedisSerializer serializer = new StringRedisSerializer();
			redisTemplate.delete(serializer.serialize(lockKey));
			System.out.println("unlock-serializer.serialize(lockKey):"+serializer.serialize(lockKey));
			System.out.println("unlock-lockKey:"+lockKey);
			redisTemplate.delete(lockKey);
			System.out.println(this.get(lockKey));
			locked = false;
		}
	}
}
