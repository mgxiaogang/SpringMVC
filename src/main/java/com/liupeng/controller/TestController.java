package com.liupeng.controller;

import com.liupeng.jdbc.IPersonDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/liupeng/test")
public class TestController {

    @Resource
    private IPersonDao personDao;

    @RequestMapping(value = "/addTest")
    public void test() {
        personDao.add();
        personDao.query();
    }
}
