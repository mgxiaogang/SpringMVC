package com.liupeng.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterAdvice;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * 问题：Spring的Bean扫描和Spring-MVC的Bean扫描是分开的, 两者的Bean位于两个不同的Application, 而且Spring-MVC的Bean扫描要早于Spring的Bean扫描,
 * 所以当Controller Bean生成完成后, 再执行Spring的Bean扫描,Spring会发现要被AOP代理的Controller Bean已经在容器中存在, 配置AOP就无效了.
 * 解决：在spring-mvc.xml中增加<aop:aspectj-autoproxy/>
 * <p>
 * xml方式配置的切面
 *
 * @author fengdao.lp
 * @Date 2018/5/24
 */
@Service
public class ControllerAspectXML implements MethodBeforeAdvice, AfterAdvice, AfterReturningAdvice {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerAspectXML.class);

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("before");
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("afterReturning");
    }

}
