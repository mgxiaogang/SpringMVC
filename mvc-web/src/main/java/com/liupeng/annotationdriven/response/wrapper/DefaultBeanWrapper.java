package com.liupeng.annotationdriven.response.wrapper;

import com.liupeng.annotationdriven.response.ResponseVo;
import org.springframework.core.MethodParameter;

/**
 * 默认BeanWrapper
 *
 * @author fengdao.lp
 * @date 2018/5/24
 */
public class DefaultBeanWrapper extends AbstractBeanWrapper {
    @Override
    public boolean supports(MethodParameter returnType) {
        return true;
    }

    @Override
    public Object wrap(Object bean) {
        return new ResponseVo(bean);
    }
}
