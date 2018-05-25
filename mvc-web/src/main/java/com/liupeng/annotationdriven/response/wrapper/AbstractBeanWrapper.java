package com.liupeng.annotationdriven.response.wrapper;

import com.liupeng.annotationdriven.response.ResponseData;
import org.springframework.core.MethodParameter;

/**
 * @author fengdao.lp
 * @date 2018/5/24
 */
public abstract class AbstractBeanWrapper implements BeanWrapper {
    @Override
    public boolean supportsType(MethodParameter returnType) {
        if (ResponseData.class.isAssignableFrom(returnType.getParameterType())) {
            return false;
        }
        return supports(returnType);
    }

    public abstract boolean supports(MethodParameter returnType);
}
