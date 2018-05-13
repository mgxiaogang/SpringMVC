package com.liupeng.advice.render;

/**
 * @author fengdao.lp
 * @date 2018/3/13
 */
public interface Render<T> {

    /**
     * 判断是否支持渲染该类型
     *
     * @param clazz 指定的类型
     * @return true:支持，false:不支持
     */
    boolean support(Class clazz);

    /**
     * 具体的渲染操作，可以替换对象
     *
     * @param obj 原始的对象
     * @return 渲染之后的对象
     */
    Object render(T obj);
}
