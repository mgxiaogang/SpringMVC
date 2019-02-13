package com.liupeng.concurrent.synchronizeds;

/**
 * @author liupeng
 * @Date 2019/2/13
 */
public class SynchronizedDemo {
    public static void main(String[] args) {
        synchronized (SynchronizedDemo.class) {
        }
        method();
    }

    private static void method() {
        System.out.println("method");
    }
}
