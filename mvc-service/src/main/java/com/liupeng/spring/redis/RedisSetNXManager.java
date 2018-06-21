package com.liupeng.spring.redis;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author fengdao.lp
 * @date 2018/6/21
 */
@Service
public class RedisSetNXManager {
    private static final Logger log = LoggerFactory.getLogger(RedisSetNXManager.class);

    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public boolean tryLock(String subKey, long timeout) {
        try {
            RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();
            if (conn != null) {
                Object redisObj = redisTemplate.opsForValue().get(subKey);
                if (redisObj != null) {
                    if ("liupeng".equals(redisObj.toString())) {
                        System.out.println(Thread.currentThread().getName() + " get oldLock succ");
                        return Boolean.TRUE;
                    } else {
                        System.out.println(Thread.currentThread().getName() + " get lock has been used");
                        return Boolean.FALSE;
                    }
                }

                Boolean boolResu = conn.setNX(subKey.getBytes(), "liupeng".getBytes());
                if (boolResu != null && boolResu) {
                    // 设置超时时间
                    redisTemplate.expire(subKey, timeout, TimeUnit.MILLISECONDS);
                    System.out.println(Thread.currentThread().getName() + " tryLock succ");
                    return Boolean.TRUE;
                } else {
                    System.out.println(Thread.currentThread().getName() + " tryLock fail");
                    // 如果操作失败，递归重试
                    if (atomicInteger.incrementAndGet() <= 2) {
                        return tryLock(subKey, timeout);
                    } else {
                        log.error("RedisLockServiceImpl@tryLock: put timeout, key:{}.", subKey);
                        System.out.println("RedisLockServiceImpl@tryLock: put timeout, key:{}." + subKey);
                    }
                }
            }
        } finally {
            //resetCounter();
        }

        return Boolean.FALSE;
    }

    public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(
        RedisTemplate<Serializable, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void unlock(String locKey) {
        redisTemplate.delete(locKey);
    }
}
