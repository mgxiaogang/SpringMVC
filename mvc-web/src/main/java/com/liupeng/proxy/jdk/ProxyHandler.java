package com.liupeng.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 *
 * @author fengdao.lp
 * @date 2018/7/2
 */
public class ProxyHandler<s> implements InvocationHandler {

    private Class<?> targetClass;

    public s createProxyInstance(Class<?> targetClass) {
        this.targetClass = targetClass;
        return (s)Proxy.newProxyInstance(targetClass.getClassLoader(), targetClass.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        method.invoke(targetClass.newInstance(), args);
        after();
        return null;
    }

    private void before() {
        System.out.println("吃饭前先洗手");
    }

    private void after() {
        System.out.println("吃饭后要洗碗");
    }

    public static void main(String[] args) throws Exception {
        ProxyHandler<People> proxyHandler = new ProxyHandler<>();
        People people = proxyHandler.createProxyInstance(Zhansan.class);
        people.eat();
    }
}
