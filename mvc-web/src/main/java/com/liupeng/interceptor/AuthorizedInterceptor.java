package com.liupeng.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizedInterceptor implements HandlerInterceptor {

    /**
     * ﻿postHandle执行后的动作
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        System.out.println("afterCompletion");
    }

    /**
     * controller执行后的动作,response装饰、解码等
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
    }

    /**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");
        HandlerMethod handlerMethod = null;
        try {
            handler = (HandlerMethod) handler;
        } catch (Exception e) {
            handlerMethod = null;
        }
        //得到方法上的TSPServiceInfo注解
        /*TSPServiceInfo tspServiceInfo = handlerMethod.getMethod().getAnnotation(TSPServiceInfo.class);
        if(null != tspServiceInfo){
            System.out.println(tspServiceInfo.name());
        }*/
        return true;
    }
}
