package com.liupeng.spring.AOP.hand;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author fengdao.lp
 * @date 2018/7/10
 */
public class DLogger implements ILogger {
    @Override
    public void start(Method method) {
        System.out.println(new Date() + method.getName() + " say hello start...");
    }

    @Override
    public void end(Method method) {
        System.out.println(new Date() + method.getName() + " say hello end");
    }
}
