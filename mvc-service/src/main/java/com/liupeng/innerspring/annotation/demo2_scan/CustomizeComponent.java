package com.liupeng.innerspring.annotation.demo2_scan;

import java.lang.annotation.*;

/**
 * @author liupeng
 * @Date 2018/12/26
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomizeComponent {
    String value() default "";
}
