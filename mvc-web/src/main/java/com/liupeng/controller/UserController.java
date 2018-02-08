package com.liupeng.controller;

import javax.annotation.Resource;

import com.liupeng.dto.User;
import com.liupeng.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/liupeng/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Resource
    private IUserService userService;

    @RequestMapping(value = "/queryUser", method = RequestMethod.GET)
    @ResponseBody
    public void test() {
        LOG.info("test");
        User user = userService.queryAll();
        LOG.info(user.toString());
    }
}
