package com.yuan.my_project.mytest.redis.bloomfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import javax.annotation.Resource;

/**
 * bitmap 应用场景：布隆过滤器、统计活跃用户、用户留存、位图计算
 * @author yuanjuntao
 * @date 2018/4/14 18:38
 */
public class RedisBloom {
    private static Logger logger = LoggerFactory.getLogger(RedisBloom.class);
    private String bitKey;

    @Resource
    private JedisPool jedisPool;


    public RedisBloom(String bitKey){
        this.bitKey = bitKey;
    }

    private Jedis getJedis(){
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
        }catch (JedisException e){
            logger.error("getJedis error:{}",e);
            if(jedis != null){
                jedisPool.returnBrokenResource(jedis);
            }
        }

        return null;
    }

    private void release(Jedis jedis, boolean isBroken){
        if(jedis != null){
            if(isBroken){
                jedisPool.returnBrokenResource(jedis);
            }else{
                jedisPool.returnResource(jedis);
            }
        }
    }


    public boolean doFilter(String item){
        boolean flag = false;
        Jedis jedis = getJedis();
        try {
            if (jedis == null)
                return flag;

            if (jedis.getbit(bitKey, randomGenerator(HashAlgorithms.java(item)))) {
                return flag;
            }

            return jedis.setbit(bitKey, randomGenerator(HashAlgorithms.java(item)), "");
        }finally {
            if(jedis!=null){
                release(jedis,true);
            }
        }
    }

    private long randomGenerator(long hashValue){
        return hashValue % (1<<32);
    }


}
