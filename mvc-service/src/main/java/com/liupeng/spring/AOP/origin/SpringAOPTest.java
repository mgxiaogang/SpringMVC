package com.liupeng.spring.AOP.origin;

import java.lang.reflect.Method;

import com.liupeng.spring.AOP.hand.Hello;
import com.liupeng.spring.AOP.hand.IHello;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

/**
 * @author fengdao.lp
 * @date 2018/7/10
 */
public class SpringAOPTest {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory(new Class[] {IHello.class});
        proxyFactory.setTarget(new Hello());
        proxyFactory.setOpaque(true);
        proxyFactory.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("前置通知");
            }
        });

        //Object proxy = proxyFactory.getProxy(proxyFactory.getClass().getClassLoader());
        Object proxy = proxyFactory.getProxy(Hello.class.getClassLoader());
        IHello iHello = (IHello)proxy;
        iHello.sayHello("liupeng");

        /**
         * 打印结果为
         * <pre>
         *     前置通知
         *     hello liupeng
         * </pre>
         */
    }
}
