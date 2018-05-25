package com.liupeng.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON注解,用于将入参对象解析为JSON对象
 *
 * @author fengdao.lp
 * @date 2018/5/24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface JSON {
    String ignore() default "";
}
