package com.liupeng.validator.annotation;


import com.liupeng.validator.CollectionNotHasNullElementValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CollectionNotHasNullElementValidator.class)
public @interface CollectionNotHasNullElement {
    int[] target() default {};

    String message() default "被检验的值不属于给定的集合";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        CollectionNotHasNullElement[] value();
    }
}
