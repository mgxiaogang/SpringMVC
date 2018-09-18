package com.liupeng.validator;

import com.google.common.primitives.Ints;
import com.liupeng.validator.annotation.CollectionNotHasNullElement;
import org.apache.commons.collections4.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 自定义异常校验
 *
 * 必选:校验集合的每一个对象都不允许为空
 * 可选:集合只允许接收指定的整数
 */
public class CollectionNotHasNullElementValidator implements ConstraintValidator<CollectionNotHasNullElement, Collection<Integer>> {

    private List<Integer> target;

    @Override
    public void initialize(CollectionNotHasNullElement constraintAnnotation) {
        int[] value = constraintAnnotation.target();
        target = Ints.asList(value);
    }

    @Override
    public boolean isValid(Collection<Integer> value, ConstraintValidatorContext context) {
        if (CollectionUtils.isNotEmpty(value)) {
            long count = value.stream().filter(Objects::isNull).count();
            if (count > 0) {
                return false;
            }
            if (CollectionUtils.isNotEmpty(target)) {
                return target.containsAll(value);
            }
        }
        return false;
    }
}
