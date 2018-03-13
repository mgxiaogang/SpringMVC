package com.liupeng.controller;

import javax.validation.Valid;

import com.liupeng.domain.ExceptionTest;
import com.liupeng.exception.ExceptionCode;
import com.liupeng.exception.MvcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author fengdao.lp
 * @date 2018/3/13
 */
@Controller
@RequestMapping("/liupeng/exception")
public class ExceptionTestController {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionTestController.class);

    @RequestMapping("/test")
    public void testException(@Valid ExceptionTest exceptionTest) {
        LOG.info(String.format("参数：%s", exceptionTest));
        throw new IllegalArgumentException("参数有误");
    }

    @RequestMapping("/test1")
    public void testException1(@Valid ExceptionTest exceptionTest) {
        System.out.println("start");
        throw new MvcException(ExceptionCode.PARAM_VALIDATE_ERROR, exceptionTest);
    }

    @RequestMapping("/test2")
    public void testException2() throws Exception {
        System.out.println("start");
        throw new Exception("出错啦");
    }

}
