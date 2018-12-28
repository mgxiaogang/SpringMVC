package com.liupeng.spring.IOC.single;

import java.lang.annotation.*;

/**
 * 自定义注解，加此注解表示要被依赖注入
 *
 * @author liupeng
 * @Date 2018/12/27
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyInject {
}
