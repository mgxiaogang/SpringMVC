package com.liupeng.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标签
 *
 * @author ethanzhou
 * @date 2018-02-05
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(EnumTags.class)
public @interface EnumTag {
    int value();
}