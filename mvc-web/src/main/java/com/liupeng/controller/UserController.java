package com.liupeng.controller;

import java.util.List;

import javax.annotation.Resource;

import com.github.pagehelper.PageInfo;
import com.liupeng.advice.annotation.Rendered;
import com.liupeng.advice.vo.Result;
import com.liupeng.annotationdriven.response.ResponseVo;
import com.liupeng.annotationdriven.response.SelfResponseJson;
import com.liupeng.controller.annotation.ControllerAnnotation;
import com.liupeng.controller.annotation.JSON;
import com.liupeng.controller.enums.ControllerEnum;
import com.liupeng.domain.UserVO;
import com.liupeng.dto.User;
import com.liupeng.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

// @RestController
@Controller
@RequestMapping(value = "/liupeng/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Resource
    private IUserService userService;

    /**
     * 1.访问路径：http://localhost:8080/liupeng/user/parseParam2?age=2&name=liupeng&date=2018-05-25%2012:00:00
     *
     * @param userVO 入参
     */
    @RequestMapping(value = "/parseParam1", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo parseParam1(@JSON(ignore = "age") UserVO userVO) {
        LOG.info("uservo:{}", com.alibaba.fastjson.JSON.toJSONString(userVO));
        return new ResponseVo(userVO);
    }

    /**
     * 1.访问路径：http://localhost:8080/liupeng/user/parseParam2?age=2&name=liupeng&date=2018-05-25%2012:00:00
     * 2.为了让自定义的结果解析生效，去除@ResponseBody
     * 3.除了String类型，其他貌似都可以作为返回值
     *
     * @param userVO 入参
     */
    @RequestMapping(value = "/parseParam2", method = RequestMethod.GET)
    @SelfResponseJson
    public UserVO parseParam2(@JSON(ignore = "age") UserVO userVO) {
        LOG.info("uservo:{}", com.alibaba.fastjson.JSON.toJSONString(userVO));
        return userVO;
    }

    @ControllerAnnotation(ControllerEnum.TEST1)
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
