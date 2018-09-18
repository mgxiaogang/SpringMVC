package com.liupeng.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.liupeng.exception.MvcException;
import org.apache.commons.collections.CollectionUtils;

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
        if (CollectionUtils.isNotEmpty(constraintViolations)) {
            StringBuilder stringBuilder = new StringBuilder();
            constraintViolations.forEach(result -> {
                stringBuilder.append(result).append("#");
            });
            throw new MvcException(stringBuilder.toString());
        }

    }
}
