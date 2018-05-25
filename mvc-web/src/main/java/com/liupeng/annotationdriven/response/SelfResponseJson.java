package com.liupeng.annotationdriven.response;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fengdao.lp
 * @date 2018/5/24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SelfResponseJson {
    boolean translate() default false;

    Location location() default Location.BYREQUEST;

    compressType compressType() default compressType.NOCOMPRESS;

    enum Location {
        UNDEFINED,
        DATA,
        MESSAGE,
        BYREQUEST
    }

    enum compressType {
        NOCOMPRESS,
        SNAPPY,
        GZIP,
        BYREQUEST
    }
}
