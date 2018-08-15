package com.liupeng.spring.beanfactory;

/**
 * @author fengdao.lp
 * @date 2018/5/15
 */
public interface Supportable<T> {
    /**
     * 标记是否支持t所表示的业务
     *
     * @param t 业务对象标记
     * @return 支持返回true，否则返回false
     */
    boolean supports(T t);
}
