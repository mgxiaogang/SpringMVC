package com.liupeng.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

import com.liupeng.controller.annotation.ControllerAnnotation;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

/**
 * 问题：Spring的Bean扫描和Spring-MVC的Bean扫描是分开的, 两者的Bean位于两个不同的Application, 而且Spring-MVC的Bean扫描要早于Spring的Bean扫描,
 * 所以当Controller Bean生成完成后, 再执行Spring的Bean扫描,Spring会发现要被AOP代理的Controller Bean已经在容器中存在, 配置AOP就无效了.
 * 解决：在spring-mvc.xml中增加<aop:aspectj-autoproxy/>
 *
 * @author fengdao.lp
 * @date 2018/5/24
 */
@Aspect
@Service
public class ControllerAspect implements Ordered {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerAspect.class);

    @Pointcut("execution(* com.liupeng.controller.*Controller.*(..))")
    private void controllerPointCut() {
    }

    //@Before("controllerPointCut()")
    //public void beforeController(JoinPoint joinPoint) throws Exception {
    //    Class<?> clazz = joinPoint.getTarget().getClass();
    //    MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
    //    LOG.info("class is :" + clazz);
    //    LOG.info("method is :" + methodSignature.getMethod().getName());
    //}

    /**
     * @param joinPoint
     * @return
     */
    @Around("controllerPointCut()")
    public Object aroundController(ProceedingJoinPoint joinPoint) {
        Class<?> clazz = joinPoint.getTarget().getClass();
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Parameter[] parameters = method.getParameters();

        ControllerAnnotation controllerAnnotation = null;
        // 类上注解
        if (clazz.isAnnotationPresent(ControllerAnnotation.class)) {
            controllerAnnotation = clazz.getAnnotation(ControllerAnnotation.class);
            LOG.info("类上存在注解:{}", controllerAnnotation.value().getName());
        } else if (method.isAnnotationPresent(ControllerAnnotation.class)) {
            controllerAnnotation = method.getAnnotation(ControllerAnnotation.class);
            LOG.info("方法上存在注解:{}", controllerAnnotation.value().getName());
        }
        if (ArrayUtils.isNotEmpty(parameters)) {
            Parameter parameter = Arrays.stream(parameters).filter(param -> {
                if (param.isAnnotationPresent(ControllerAnnotation.class)) {
                    LOG.info("参数上存在注解:{}", param.getAnnotation(ControllerAnnotation.class).value().getName());
                    return true;
                }
                return false;
            }).findAny().orElse(null);
        }
        if (null == controllerAnnotation) {
            LOG.error("类或方法上不存在注解");
        }

        Object retValue = null;
        try {
            retValue = joinPoint.proceed();
        } catch (Throwable throwable) {
            LOG.error("execute error");
        }
        return retValue;
    }

    /**
     * 控制顺序,在事务aop前加载
     * <tx:annotation-driven transaction-manager="xxxxxTransactionManager" order="1"/>
     *
     * @return int
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
