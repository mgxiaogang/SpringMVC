package com.liupeng.spring.AOP.hand;

import java.lang.reflect.Method;

/**
 * @author fengdao.lp
 * @date 2018/7/10
 */
public interface ILogger {
    void start(Method method);

    void end(Method method);
}
