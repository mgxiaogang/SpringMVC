package com.liupeng.proxy.cglib;

import com.liupeng.advice.vo.User;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;
import net.sf.cglib.reflect.FastMethod;

/**
 * @author fengdao.lp
 * @date 2018/5/28
 */
public class ReflectTest {

    /**
     * cglib反射-创建对象实例
     * <code>User aa = User.class.newInstance()</code>
     */
    public static void main(String[] args) throws Exception {
        createObject();
        invokeMethod();
    }

    private static void invokeMethod() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        User user = new User();
        user.setId(1);
        user.setName("liupeng");
        FastClass fastClass = FastClass.create(classLoader, User.class);

        // 获得getName方法，每次都会新建一个FastMethod对象,底层仍然继续jdk-reflect
        FastMethod fastMethod = fastClass.getMethod("getName", new Class[] {});
        String result = (String)fastMethod.invoke(user, null);
        System.out.println(result);
    }

    private static void createObject() throws Exception {
        // 创建fastClass实例,底层将根据TestObject.class字节码信息创建一个新的class:
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        FastClass fastClass = FastClass.create(classLoader, User.class);
        // default constructor
        FastConstructor fastConstructor = fastClass.getConstructor(new Class[] {});
        User user = (User)fastConstructor.newInstance();
        System.out.println(user);
        // 直接通过newInstance也可以创建对象，底层实现一样，找到默认的FastConstructor
        User user1 = (User)fastClass.newInstance();
        System.out.println(user1);
    }
}
