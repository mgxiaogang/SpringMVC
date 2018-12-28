package com.liupeng.spring.IOC.full;

import java.lang.annotation.*;

/**
 * 标注此注解的类中所有属性会自动注入
 *
 * @author liupeng
 * @Date 2018/12/28
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FullInject {
}
