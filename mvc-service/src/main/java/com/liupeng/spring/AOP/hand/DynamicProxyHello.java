package com.liupeng.spring.AOP.hand;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author fengdao.lp
 * @date 2018/7/10
 */
public class DynamicProxyHello<s> implements InvocationHandler {
    //调用对象
    private Class<?> proxy;
    /**
     * 目标对象
     */
    private Class<?> targetClass;

    public s createProxyInstance(Class<?> targetClass, Class<?> proxy) {
        this.targetClass = targetClass;
        this.proxy = proxy;
        return (s)Proxy.newProxyInstance(targetClass.getClassLoader(), targetClass.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        //反射得到操作者的Start方法
        Method start = this.proxy.getDeclaredMethod("start", new Class[] {Method.class});
        //反射执行start方法
        start.invoke(this.proxy, new Object[] {method});
        //执行要处理对象的原本方法
        method.invoke(this.targetClass.newInstance(), args);
        //反射得到操作者的end方法
        Method end = this.proxy.getDeclaredMethod("end", new Class[] {Method.class});
        //反射执行end方法
        end.invoke(this.proxy, new Object[] {method});
        return result;
    }

    private void before() {
        System.out.println("吃饭前先洗手");
    }

    private void after() {
        System.out.println("吃饭后要洗碗");
    }

    public static void main(String[] args) throws Exception {
        DynamicProxyHello<IHello> proxyHandler = new DynamicProxyHello<>();
        IHello people = proxyHandler.createProxyInstance(Hello.class, new DLogger());
        people.sayHello("liupeng");
    }
}
