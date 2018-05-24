package com.liupeng.controller.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.liupeng.controller.enums.ControllerEnum;

/**
 * @author fengdao.lp
 * @date 2018/5/24
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerAnnotation {
    ControllerEnum value();
}
