package com.liupeng.spring.AOP.hand;

/**
 * @author fengdao.lp
 * @date 2018/7/10
 */
public class Hello implements IHello {
    @Override
    public void sayHello(String str) {
        System.out.println("hello " + str);
    }
}
