package com.liupeng.RPC;

import java.net.InetSocketAddress;

/**
 * Created by liupeng on 2017/6/19.
 */
public class RpcTest {
    public static void main(String[] args) {
        /**
         * 首先创建一个异步发布服务端的线程并启动，用于接收RPC客户端的请求，根据请求参数调用服务实现类，返回结果给客户端
         */
        new Thread(new Runnable() {
            public void run() {
                try {
                    RpcExporter.exporter("localhost", 8088);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        /**
         * 创建客户端服务代理类，构造RPC请求参数，发起RPC调用，将调用结果输出到控制台上，执行结果如下：
         */
        RpcImporter<EchoService> importer = new RpcImporter<EchoService>();
        EchoService echo = importer.importer(EchoServiceImpl.class, new InetSocketAddress("localhost", 8088));
        System.out.println(echo.echo("Are you OK?"));
    }
}
