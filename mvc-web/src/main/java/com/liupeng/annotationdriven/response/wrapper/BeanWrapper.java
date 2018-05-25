package com.liupeng.annotationdriven.response.wrapper;

import org.springframework.core.MethodParameter;

/**
 * 对象包装器
 *
 * @author fengdao.lp
 * @date 2018/5/24
 */
public interface BeanWrapper {

    /**
     * 支持性判断
     *
     * @param returnType
     * @return
     */
    boolean supportsType(MethodParameter returnType);

    /**
     * 对象包装
     *
     * @param bean
     * @return
     */
    Object wrap(Object bean);
}
