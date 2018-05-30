package com.liupeng.controller.spring;

import com.liupeng.spring.springCache.SpringCacheTestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author fengdao.lp
 * @date 2018/5/30
 */
@Controller
@RequestMapping("/liupeng/springCache")
public class SpringCacheTestController {
    private static final Logger LOG = LoggerFactory.getLogger(SpringCacheTestController.class);

    @Autowired
    private SpringCacheTestServiceImpl springCacheTestService;

    @RequestMapping("/test")
    public void test() {
        springCacheTestService.find1(1);
    }
}
