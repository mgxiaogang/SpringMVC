package com.liupeng.controller.spring.redis;

import com.liupeng.spring.redis.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author fengdao.lp
 * @date 2018/6/20
 */
@Controller
@RequestMapping("/redis")
public class SpringRedisController {

    @Autowired
    private RedisManager redisManager;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() {
        String key = "redisTest";
        boolean flag = redisManager.exists(key);
        System.out.println(flag);

        if (!flag) {
            redisManager.setForValue(key, "liupeng");
        }
        System.out.println(redisManager.getForValue(key, String.class));
    }
}
