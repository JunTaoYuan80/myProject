package com.yuan.my_project.mytest.redis.distributelock;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;


public class Test {
	public static final String LOCK_KEY = "redis_key_test";//多个JVM的公共常量

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate<String, String> redisTemplate = (RedisTemplate<String, String>)ctx.getBean("redisTemplate");
		redisTemplate.afterPropertiesSet();
		RedisLock lock = new RedisLock(redisTemplate, LOCK_KEY);
		String oldTime = "";
		try{
			if(lock.lock()){
				oldTime = lock.get(lock.getLockKey());
				System.out.println("thread name:"+Thread.currentThread().getName()+" get lock success");
				Thread.sleep(5000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(StringUtils.isNotBlank(oldTime)){
				String curTime = lock.get(lock.getLockKey());
				if(oldTime.equals(curTime)){
					lock.unlock();
				}
			}
			
		}
	}

}
