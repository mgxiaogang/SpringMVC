package com.liupeng.spring.beanRegister;

import java.lang.annotation.*;

/**
 * @author liupeng
 * @Date 2018/12/26
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeanRegisterAnnotation {
    String value() default "";
}
