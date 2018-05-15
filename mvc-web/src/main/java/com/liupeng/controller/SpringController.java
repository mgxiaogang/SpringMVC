package com.liupeng.controller;

import com.liupeng.spring.listener.MyApplicationEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试Spring
 *
 * @author fengdao.lp
 * @date 2018/5/15
 */
@Controller
@RequestMapping("/liupeng/spring")
public class SpringController {
    private static final Logger LOG = LoggerFactory.getLogger(SpringController.class);

    @Autowired
    private MyApplicationEventPublisher myApplicationEventPublisher;

    @RequestMapping("/applicationEvent")
    public void testApplicationEvent() {
        myApplicationEventPublisher.publish("测试");
        LOG.info("测试成功");
    }

}
