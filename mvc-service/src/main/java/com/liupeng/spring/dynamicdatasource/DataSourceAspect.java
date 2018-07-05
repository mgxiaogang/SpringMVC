package com.liupeng.spring.dynamicdatasource;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

/**
 * 拦截service
 * 1.service实现类上有@DataSource注解,则根据注解内容切换数据源
 * 2.service方法之上有@DataSource注解,则根据注解内容切换数据源(覆盖类上的注解)
 * 3.都不存在注解,则使用默认数据源
 *
 * @author fengdao.lp
 * @date 2018/1/2
 */
@Aspect
@Service
public class DataSourceAspect implements Ordered {
    private static final Logger LOG = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("execution(* com.liupeng.service..*Service.*(..))")
    private void dataSourcePointCut() {
    }

    @Before("dataSourcePointCut()")
    public void dynamicSwitchDataSource(JoinPoint joinPoint) throws Exception {
        // 代理相关
        System.out.println("被代理的对象：" + joinPoint.getTarget());
        System.out.println("代理对象自己：" + joinPoint.getThis());
        // 目标类相关
        System.out.println("目标方法所属类的简单类名：" + joinPoint.getSignature().getDeclaringType().getSimpleName());
        System.out.println("目标方法所属类的类名：" + joinPoint.getSignature().getDeclaringTypeName());
        System.out.println("目标方法声明类型：" + Modifier.toString(joinPoint.getSignature().getModifiers()));
        System.out.println("目标方法名为：" + joinPoint.getSignature().getName());
        System.out.println("目标方法参数为：" + Arrays.toString(joinPoint.getArgs()));

        // 使用
        Class<?> target = joinPoint.getTarget().getClass();
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = Arrays.stream(target.getDeclaredMethods())
            .filter(mt -> mt.getName().equalsIgnoreCase(methodSignature.getMethod().getName()))
            .findFirst()
            .orElse(null);
        resolveDataSource(target, method);
    }

    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            DataSource dataSource = null;
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(DataSource.class)) {
                dataSource = clazz.getAnnotation(DataSource.class);
                DynamicDataSourceHolder.setDataSource(dataSource.value().getName());
            }
            // 如果方法上也存在注解，则优先使用方法上的注解
            if (method.isAnnotationPresent(DataSource.class)) {
                dataSource = method.getAnnotation(DataSource.class);
                DynamicDataSourceHolder.setDataSource(dataSource.value().getName());
            }
            if (null == dataSource) {
                DynamicDataSourceHolder.setDataSource(DbEnum.WK_DATA_SOURCE.getName());
            }
        } catch (Exception e) {
            LOG.error("拦截DataSource注解失败,clazz:{},错误:{}", clazz, e);
        }
    }

    /**
     * 控制顺序,在事务aop前加载
     *
     * @return order
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
