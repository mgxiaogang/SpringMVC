package com.liupeng.aop;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

import com.google.common.base.Preconditions;
import com.liupeng.controller.annotation.ControllerAnnotation;
import com.liupeng.controller.annotation.JSON;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
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

    /**
     * @param joinPoint joinPoint
     * @return 返回
     */
    @Around("controllerPointCut()")
    public Object aroundController(ProceedingJoinPoint joinPoint) throws Exception {
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
            for (Parameter parameter : parameters) {
                if (parameter.isAnnotationPresent(JSON.class)) {
                    LOG.info("参数上存在JSON注解");
                    parseParam(parameter, parameter.getAnnotation(JSON.class));
                }
            }
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
     * 将入参解析到VO中
     *
     * @param parameter  入参
     * @param annotation 注解
     */
    private void parseParam(Parameter parameter, JSON annotation) throws Exception {
        Class<?> clazz = parameter.getType();
        // 获取FastConstructor
        FastConstructor fastConstructor = getFastConstructor(clazz);
        // 获取实例
        Object object = getNewInstance(fastConstructor);

        if (null != object) {
            BeanWrapper beanWrapper = new BeanWrapperImpl(object);
            PropertyDescriptor[] propertyDescriptor = beanWrapper.getPropertyDescriptors();
            // BeanWrapper具体用法看blowfish
        } else {
            LOG.error("object is null");
        }
    }

    /**
     * 创建实例
     *
     * @param fastConstructor 构造器
     * @return 实例
     * @throws Exception 异常
     */
    private static Object getNewInstance(FastConstructor fastConstructor) throws Exception {
        try {
            return fastConstructor.newInstance();
        } catch (InvocationTargetException e) {
            throw new Exception("Failed to create instance of class " + fastConstructor.getDeclaringClass().getName(),
                e.getCause());
        }
    }

    /**
     * 根据对象类型获取对象构造器
     *
     * @param beanType 对象类型
     * @return 构造器
     */
    private static FastConstructor getFastConstructor(Class<?> beanType) {
        int mod = beanType.getModifiers();
        Preconditions.checkArgument(!Modifier.isAbstract(mod) && Modifier.isPublic(mod),
            "Class to set properties should be public and concrete: %s", beanType.getName());
        Constructor<?> constructor;
        try {
            constructor = beanType.getConstructor();
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Class to set properties has no default constructor: %s",
                beanType.getName()));
        }
        return FastClass.create(beanType).getConstructor(constructor);
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
