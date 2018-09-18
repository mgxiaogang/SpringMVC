package com.liupeng.validator;

import com.liupeng.exception.MvcException;
import org.apache.commons.collections.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Bean校验Util
 *
 * @author fengdao.lp
 * @date 2018/8/8
 */
public class BeanValidatorUtil {

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public static synchronized Validator getValidator() {
        return validatorFactory.getValidator();
    }

    /**
     * 校验方法
     *
     * @param t   待校验对象
     * @param <T> 类型
     */
    public static <T> void validate(T t) {
        Set<ConstraintViolation<T>> constraintViolations = getValidator().validate(t);
        assembleResult(constraintViolations);
    }

    /**
     * 校验方法
     *
     * @param t   待校验对象
     * @param <T> 类型
     */
    public static <T, K> void validateByGroup(T t, Class<K> clazz) {
        Set<ConstraintViolation<T>> constraintViolations = getValidator().validate(t, clazz);
        assembleResult(constraintViolations);
    }

    /**
     * 处理教研结果集
     */
    private static <T> void assembleResult(Set<ConstraintViolation<T>> constraintViolations) {
        if (CollectionUtils.isNotEmpty(constraintViolations)) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                sb.append(constraintViolation.getMessage()).append(", ");
            }
            String message = sb.toString();
            throw new MvcException(sb.toString());
        }
    }
}