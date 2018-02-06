package main.devops.RPC;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by liupeng on 2017/6/19.
 * RPC客户端本地服务代理源码如下：
 */
public class RpcImporter<S> implements InvocationHandler {
    private Class<?> serviceClass;
    private InetSocketAddress address;

    public S importer(final Class<?> serviceClass, final InetSocketAddress addr) {
        this.serviceClass = serviceClass;
        this.address = addr;
        /**
         * 将本地的接口调用（调用远程服务，即测试类中的echo.echo）转换成JDK的动态代理，在动态代理中实现接口的远程调用（下面的invoke中）<br/用>
         * 调用者调用的服务实际是远程服务的本地代理，将本地调用封装成远程服务调用，好处是调用者不需要知道底层通信细节和调用过程
         */
        return (S) Proxy.newProxyInstance(serviceClass.getClassLoader(), serviceClass.getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Socket socket = null;
        ObjectOutputStream output = null;
        ObjectInputStream input = null;
        try {
            /**
             * 创建socket客户端，根据指定地址连接远程服务提供者
             */
            socket = new Socket();
            socket.connect(address);
            /**
             * 将远程服务调用所需要的接口类、方法名、参数列表等编码后发送给服务提供者
             */
            output = new ObjectOutputStream(socket.getOutputStream());
            output.writeUTF(serviceClass.getName());
            output.writeUTF(method.getName());
            output.writeObject(method.getParameterTypes());
            output.writeObject(args);
            /**
             * 同步阻塞等待服务端返回应答，获取应答后返回
             */
            input = new ObjectInputStream(socket.getInputStream());
            return input.readObject();
        } finally {
            if (socket != null) {
                socket.close();
            }
            if (output != null) {
                output.close();
            }
            if (input != null) {
                input.close();
            }
        }
    }
}
