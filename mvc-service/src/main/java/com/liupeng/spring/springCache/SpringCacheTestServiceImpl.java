package com.liupeng.spring.springCache;

import com.liupeng.dto.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author fengdao.lp
 * @date 2018/5/30
 */
@Service
public class SpringCacheTestServiceImpl {
    @Cacheable(value = "simple", key = "#id")
    public User find1(Integer id) {
        return null;
    }

    @Cacheable(value = "simple", key = "#p0")
    public User find2(Integer id) {
        return null;
    }

    @Cacheable(value = "simple", key = "#user.name")
    public User find3(User user) {
        return new User();
    }

    @Cacheable(value = "simple", key = "#p0.name")
    public User find4(User user) {
        return null;
    }

    /**
     * 缓存的配置在springCache-bean.xml中配置的
     */
    public User findById(User user) {
        return null;
    }
    /**
     * 缓存的配置在springCache-bean.xml中配置的
     */
    public User findByName(User user) {
        return null;
    }
}
