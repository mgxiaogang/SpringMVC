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

    @Cacheable(value = "simple", key = "#user.id")
    public User find3(User user) {
        return null;
    }

    @Cacheable(value = "simple", key = "#p0.id")
    public User find4(User user) {
        return null;
    }
}
