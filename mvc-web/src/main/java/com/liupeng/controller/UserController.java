package com.liupeng.controller;

import java.util.List;

import javax.annotation.Resource;

import com.github.pagehelper.PageInfo;
import com.liupeng.advice.annotation.Rendered;
import com.liupeng.advice.vo.Result;
import com.liupeng.dto.User;
import com.liupeng.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/liupeng/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Resource
    private IUserService userService;

    @RequestMapping(value = "/queryUser", method = RequestMethod.GET)
    @ResponseBody
    public void test() {
        LOG.info("testxufan");
        List<User> user = userService.queryAll();
        LOG.info(user.toString());
    }

    @RequestMapping(value = "/testTkMapper", method = RequestMethod.GET)
    @ResponseBody
    public void testTkMapper() {
        // 新增
        User user1 = new User();
        user1.setAge(21);
        user1.setName("xufan");
        //userService.insert(user1);

        // 查询一条记录
        User user = new User();
        user.setAge(21);
        User u = userService.selectOne(user);
        //LOG.info(u.toString());

        // 根据主键删除一条记录
        //userService.deleteByPrimaryKey(3L);

        // 分页查询
        PageInfo<User> userPageInfo = userService.selectByPage();
        LOG.info(userPageInfo.toString());
    }

    @RequestMapping(value = "/responseRenderTest", method = RequestMethod.GET)
    @ResponseBody
    @Rendered
    public Result responseRenderTest() {
        com.liupeng.advice.vo.User user = new com.liupeng.advice.vo.User();
        user.setId(1);
        user.setRootId("2");
        user.setAge("26");
        user.setName("liupeng");
        user.setHeight("181");
        return Result.buildSuccessResultOf(user);
    }
}
