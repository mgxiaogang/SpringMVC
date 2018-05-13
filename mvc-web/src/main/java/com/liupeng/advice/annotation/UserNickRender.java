package com.liupeng.advice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 工号渲染为nick
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserNickRender {

    /**
     * 指定替换之后的属性名
     * 如果指定的属性，在实例中不存在，则动态代理添加一个实例
     *
     * @return 属性名称
     */
    String aliasField() default "";
}
