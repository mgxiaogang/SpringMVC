package com.liupeng.controller;

import javax.annotation.Resource;

import com.liupeng.service.IDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

// @RestController
@Controller
@RequestMapping(value = "/liupeng/demo")
public class DemoController {

    private static final Logger LOG = LoggerFactory.getLogger(DemoController.class);

    @Resource
    private IDemoService demoService;

    @RequestMapping(value = "/queryUser", method = RequestMethod.GET)
    @ResponseBody
    public void test() {
        demoService.say2();
    }
}
