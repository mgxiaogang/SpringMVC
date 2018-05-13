package com.liupeng.advice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记是否需要对当前的返回对象进行渲染
 * 具体的渲染可以在 {@link com.liupeng.advice}
 *
 * @author liupeng
 * @date 22/02/2018 17:55
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Rendered {
}
