package com.liupeng.controller.spring;

import com.liupeng.domain.UserVO;
import com.liupeng.validator.BeanValidatorUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author fengdao.lp
 * @date 2018/8/8
 */
@Controller
@RequestMapping(value = "/liupeng/validator")
public class ValidatorController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(UserVO userVO) {
        BeanValidatorUtil.validate(userVO);
        System.out.println("haha");
    }
}
