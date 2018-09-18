package com.liupeng.advice;

import com.liupeng.domain.ExceptionTest;
import com.liupeng.exception.MvcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fengdao.lp
 * @date 2018/3/13
 */
@ControllerAdvice
@Service
public class ExceptionHandlerControllerAdvice {
    private final Logger logger = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ExceptionTest bindExceptionHandle(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        logger.error("resolve argument fail" + bindingResult);
        return ExceptionTest.ExceptionTestBuilder.newExceptionTest().withId(1L).withName("test").build();
    }

    @ExceptionHandler(MvcException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public void mvcExceptionHandler(HttpServletRequest request, HttpServletResponse response, MvcException e)
            throws IOException {
        logger.error("错误码为：" + e.getCode(), e);
        response.getWriter().write("error");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public void exceptionHandle(HttpServletResponse response, Exception e) throws IOException {
        logger.error("error", e);
        response.getWriter().write("error");
    }
}
