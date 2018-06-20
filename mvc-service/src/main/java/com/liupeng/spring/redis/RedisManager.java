package com.liupeng.spring.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author fengdao.lp
 * @date 2018/6/20
 */
@Service
public class RedisManager {
    private static final Logger logger = LoggerFactory.getLogger(RedisManager.class);

    private final RedisTemplate<Serializable, Serializable> template;

    @Autowired
    public RedisManager(RedisTemplate<Serializable, Serializable> template) {this.template = template;}

    /**
     * 【字符串类型】判断缓存中是否有对应的value
     */
    public boolean exists(final String key) {
        return template.hasKey(key);
    }

    /**
     * 【字符串类型】批量删除符合表达式的key
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = template.keys(pattern);
        if (keys.size() > 0) { template.delete(keys); }
    }

    /**
     * 【字符串类型】删除对应的value
     */
    public void remove(final String key) {
        if (exists(key)) {
            template.delete(key);
        }
    }

    /**
     * 【字符串类型】添加单个key value
     */
    public void setForValue(final Serializable key, final Serializable value) {
        template.opsForValue().set(key, value);
    }

    /**
     * 【字符串类型】添加单个key value，并设置超时时间
     */
    public void setForValue(final Serializable key, final Serializable value, long timeout, TimeUnit unit) {
        template.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 【字符串类型】添加多组key value
     */
    public void setForValues(Map<? extends Serializable, ? extends Serializable> m) {
        template.opsForValue().multiSet(m);
    }

    /**
     * 【字符串类型】添加多组key value，并设置超时时间
     */
    public void setForValues(Map<? extends Serializable, ? extends Serializable> m, long timeout, TimeUnit unit) {
        for (Serializable key : m.keySet()) {
            template.opsForValue().set(key, m.get(key), timeout, unit);
        }
    }

    /**
     * 【字符串类型】添加多组key value，并设置超时时间
     */
    public void setForValueList(final Map<Serializable, Serializable> map, long timeout, TimeUnit unit) {
        try {
            for (Map.Entry<Serializable, Serializable> entry : map.entrySet()) {
                template.opsForValue().set(entry.getKey(), entry.getValue(), timeout, unit);
            }
        } catch (Exception e) {
            logger.error("RedisManager.setForValueList异常: ", e);
        }
    }

    /**
     * 【字符串类型】根据单个key获取value
     */
    public <T> T getForValue(final Serializable key, Class<T> cls) {
        Serializable obj = template.opsForValue().get(key);
        if (obj != null) {
            return cls.cast(obj);
        } else {
            return null;
        }
    }

    /**
     * 【字符串类型】根据多个key获取value集合
     */
    public <T> List<T> getForValues(final Collection<Serializable> keys, Class<T> cls) {
        List<Serializable> obj = template.opsForValue().multiGet(keys);
        List<T> result = new ArrayList<T>();
        if (null != obj) {
            for (Serializable s : obj) {
                if (null != s && !"null".equals(s)) {
                    result.add(cls.cast(s));
                }
            }
            return result;
        } else {
            return null;
        }

    }

    /**
     * 【字符串类型】根据多个key获取value集合
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getForValueList(final Collection<Serializable> keys, Class<T> cls) {
        try {
            List<Serializable> tmpList = template.opsForValue().multiGet(keys);
            List<Serializable> result = new ArrayList<Serializable>();
            for (Serializable serializable : tmpList) {
                if (null != serializable) {
                    result.add(serializable);
                }
            }
            return (List<T>)result;
        } catch (Exception e) {
            return new ArrayList<T>();
        }
    }

    /**
     * 【字符串类型】添加单个key value，value为集合
     */
    @SuppressWarnings("rawtypes")
    public void setForValueList(final Serializable key, List<? extends Serializable> value, long timeout,
                                TimeUnit unit) {
        RedisVo rv = new RedisVo<Serializable>(value);
        template.opsForValue().set(key, rv, timeout, unit);
    }

    /**
     * 【字符串类型】获取单个key value，value为集合
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T> List<T> getForValueList(final Serializable key, Class<T> cls) {
        Serializable obj = template.opsForValue().get(key);
        if (obj != null) {
            return ((RedisVo)obj).getValue();
        }
        return null;
    }

    /**
     * 【hash】设置key value
     */
    public <K, V> void setForValueMap(final Serializable key, Map<K, V> map, long timeout, TimeUnit unit) {
        try {
            template.multi();
            template.opsForHash().putAll(key, map);
            template.expire(key, timeout, unit);
            template.exec();
        } catch (Exception e) {
            template.discard();
        }
    }

    /**
     * 【hash】获取key value
     */
    @SuppressWarnings("unchecked")
    public <K, V> Map<K, V> getForValueMap(final Serializable key) {
        Map<?, ?> obj = template.opsForHash().entries(key);
        try {
            return (Map<K, V>)obj;
        } catch (Exception e) {
            return new HashMap<K, V>();
        }
    }
}
