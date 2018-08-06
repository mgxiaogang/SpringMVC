package com.liupeng.concurrent.自增ID;

/**
 * 变量i，10个线程分别对其加10次，最后结果应i为100
 *
 * @author fengdao.lp
 * @date 2018/8/4
 */
public class ThreadTest1 implements Runnable {
    private Node node;

    public ThreadTest1(Node node) {
        this.node = node;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            node.add();
            System.out.println(Thread.currentThread().getName() + ": " + node.getI());
        }
    }

    public static void main(String[] args) {
        Node node1 = new Node();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new ThreadTest1(node1), "thread-" + i);
            thread.start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(node1.getI());
    }
}
