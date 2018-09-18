package com.liupeng.controller.spring;

import com.liupeng.domain.UserVO;
import com.liupeng.validator.BeanValidatorUtil;
import com.liupeng.validator.group.CommonGroups;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.groups.Default;

/**
 * @author fengdao.lp
 * @date 2018/8/8
 */
@Controller
@RequestMapping(value = "/liupeng/validator")
public class ValidatorController {

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public void test1(@Validated({CommonGroups.Save.class, Default.class}) UserVO userVO) {
        System.out.println(userVO.toString());
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public void test2(UserVO userVO) {
        BeanValidatorUtil.validateByGroup(userVO, CommonGroups.Update.class);
        System.out.println(userVO.toString());
    }
}
