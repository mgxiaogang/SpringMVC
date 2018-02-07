package com.liupeng.controller;

import javax.annotation.Resource;

import com.liupeng.jdbc.IPersonDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/liupeng/test")
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Resource
    private IPersonDao personDao;

    @RequestMapping(value = "/addTest", method = RequestMethod.GET)
    @ResponseBody
    public void test() {
        LOG.info("test");
        personDao.query();
    }
}
