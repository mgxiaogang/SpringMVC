package NIO;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioServer {
    private Selector selector;
    private ExecutorService tp = Executors.newCachedThreadPool();
    public static Map<Socket, Long> time_stat = new HashMap<Socket, Long>();

    private void startServer() throws Exception {
        // 通过工厂方法获取一个Selector对象的实例
        selector = SelectorProvider.provider().openSelector();
        // 获得表示服务端的SocketChannel实例
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 将这个SocketChannel设置为非阻塞模式。实际上Channel也可以像socket那样按照阻塞的方式工作，但这里更倾向于非阻塞模式
        ssc.configureBlocking(false);
        // 端口绑定，将这个Channel绑定在8000端口
        InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 8000);
        ssc.socket().bind(isa);
        /**
         * 将这个ServerSocketChannel绑定到Selector上，并注册它感兴趣的时间为Accept。<br/>
         * 当Selector发现ServerSocketChannel有新的客户端链接的时候，就会通知ServerSocketChannel进行处理<br/>
         * 方法register的返回值是一个SelectionKey，SelectionKey表示一对Selector和Channel的关系，当Channel注册到Selector上时，就相当于确立了两者的服务关系，SelectionKey就是这个契约<br/>
         *
         *
         */
        SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);

        // 无穷循环，等待-分发网络消息(系统准备好IO后再来通知我们的线程读取或者写入)
        for (; ; ) {
            // select()方法是一个阻塞方法，如果当前数据没有准备好则等待。一旦有数据可读，就立即返回，返回值为已经准备就绪的SelectionKey的数量
            selector.select();
            // 读取准备好的SelectionKey（因为一个Selector对应多个Channel服务，此处是集合）
            Set readyKeys = selector.selectedKeys();
            //迭代SelectionKey
            Iterator i = readyKeys.iterator();
            long e = 0;
            while (i.hasNext()) {
                SelectionKey sk = (SelectionKey) i.next();
                // 务必要移除，否则会重复处理相同的SelectionKey
                i.remove();
                // 判断当前SelectionKey所代表的Channel是否在Acceptable状态，如果是就进行客户端接受
                if (sk.isAcceptable()) {
                    //doAccept(sk);
                }
                // 判断Channel是否已经可读了，如果是就进行读取
                else if (sk.isValid() && sk.isReadable()) {
                    if (!time_stat.containsKey(((SocketChannel) sk.channel()).socket())) {
                        time_stat.put(((SocketChannel) sk.channel()).socket(), System.currentTimeMillis());
                    }
                    //doRead(sk);
                }
                // 判断Channel是否准备好进行些，如果是就写入
                else if (sk.isValid() && sk.isWritable()) {
                    //doWrite(sk);
                    e = System.currentTimeMillis();
                    long b = time_stat.remove(((SocketChannel) sk.channel()).socket());
                    System.out.println("spend:" + (e - b) + "ms");
                }
            }

        }
    }
}
