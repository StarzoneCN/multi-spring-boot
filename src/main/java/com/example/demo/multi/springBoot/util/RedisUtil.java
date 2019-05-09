package com.example.demo.multi.springBoot.util;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    private final static Logger LOGGER = Logger.getLogger(RedisUtil.class);
    private static final String MSG_ERR_REDIS_POOL_INIT_FAILURE = "Redis-pool初始化失败";
    private static final String MSG_ERR_FAIL_TO_CREATRE_REDIS_CONNECTION = "Redis连接创建失败";
    private static final String MSG_WARN_NO_REDIS_RESOURCE = "无Redis连接资源";

    private RedisUtil(){

    }
    private static class InnerContainer {
        final static JedisPool JEDIS_POOL = RedisUtil.init();
    }

    //Redis服务器IP
    private static String ADDR = "192.168.234.33";
    //Redis的端口号
    private static Integer PORT = 56379;
    //访问密码
    private static String AUTH = "Mon56BuEcXzZ";

    //可用连接实例的最大数目，默认为8；
    //如果赋值为-1，则表示不限制，如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
    private static Integer MAX_TOTAL = 1024;
    //控制一个pool最多有多少个状态为idle(空闲)的jedis实例，默认值是8
    private static Integer MAX_IDLE = 200;
    //等待可用连接的最大时间，单位是毫秒，默认值为-1，表示永不超时。
    //如果超过等待时间，则直接抛出JedisConnectionException
    private static Integer MAX_WAIT_MILLIS = 10000;
    private static Integer TIMEOUT = 10000;
    private static Integer DEFUAL_DB = 10;
    //在borrow(用)一个jedis实例时，是否提前进行validate(验证)操作；
    //如果为true，则得到的jedis实例均是可用的
    private static Boolean TEST_ON_BORROW = true;

    private static JedisPool init(){
        JedisPoolConfig config = new JedisPoolConfig();
        /*注意：
            在高版本的jedis jar包，比如本版本2.9.0，JedisPoolConfig没有setMaxActive和setMaxWait属性了
            这是因为高版本中官方废弃了此方法，用以下两个属性替换。
            maxActive  ==>  maxTotal
            maxWait==>  maxWaitMillis
         */
        config.setMaxTotal(MAX_TOTAL);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWaitMillis(MAX_WAIT_MILLIS);
        config.setTestOnBorrow(TEST_ON_BORROW);
        try {
            return new JedisPool(config,ADDR,PORT,TIMEOUT,AUTH, DEFUAL_DB);
        } catch (Exception e) {
            LOGGER.error(MSG_ERR_REDIS_POOL_INIT_FAILURE, e);
            return null;
        }
    }

    private synchronized static Jedis jedis(){
        try {
            if(InnerContainer.JEDIS_POOL != null){
                Jedis jedis = InnerContainer.JEDIS_POOL.getResource();
                return jedis;
            }else{
                return null;
            }
        } catch (Exception e) {
            LOGGER.error(MSG_ERR_FAIL_TO_CREATRE_REDIS_CONNECTION, e);
            return null;
        }
    }

    private static void closeJedis(final Jedis jedis){
        //方法参数被声明为final，表示它是只读的。
        if(jedis!=null){
            //jedis.close()取代jedisPool.returnResource(jedis)方法将3.0版本开始
            jedis.close();
        }
    }

    public static boolean exists(String key){
        try(Jedis jedis = jedis()) {
            if (jedis == null){
                LOGGER.warn(MSG_WARN_NO_REDIS_RESOURCE);
                return false;
            }
            return jedis.exists(key);
        }
    }

    public static long setTimeOut(String key, int expire){
        try(Jedis jedis = jedis()) {
            if (jedis == null){
                LOGGER.warn(MSG_WARN_NO_REDIS_RESOURCE);
                return -1;
            }
            return jedis.expire(key, expire);
        }
    }

    public static String get(String key){
        try(Jedis jedis = jedis()) {
            if (jedis == null){
                LOGGER.warn(MSG_WARN_NO_REDIS_RESOURCE);
                return null;
            }
            return jedis.get(key);
        }
    }
}
