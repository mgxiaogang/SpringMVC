package com.liupeng.proxy.cglib.enhancer;

import java.lang.reflect.Method;

import com.liupeng.proxy.jdk.Zhansan;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * cglib动态代理
 *
 * @author fengdao.lp
 * @date 2018/7/2
 */
public class CGlibProxyFactory implements MethodInterceptor {

    private Class<?> targetClass;

    public Object createProxyInstance(Class<?> targetClass) {
        this.targetClass = targetClass;
        // 生成代理对象
        Enhancer enhancer = new Enhancer();
        // 设置父类为目标类，对目标类中的非final方法修饰符的所有方法覆盖，并添加一些自身代码
        enhancer.setSuperclass(targetClass);
        // 回调
        enhancer.setCallback(this);
        // 通过字节码技术动态创建子类实例
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("前置代理");
        Object result = methodProxy.invokeSuper(obj, args);
        System.out.println("后置代理");
        return result;
    }

    public static void main(String[] args) throws Exception {
        CGlibProxyFactory cGlibProxyFactory = new CGlibProxyFactory();
        Zhansan zhansan = (Zhansan)cGlibProxyFactory.createProxyInstance(Zhansan.class);
        zhansan.eat();
    }
}