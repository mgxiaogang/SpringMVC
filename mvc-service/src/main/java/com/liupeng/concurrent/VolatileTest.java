package com.liupeng.concurrent;

/**
 * @author fengdao.lp
 * @date 2018/8/10
 */
public class VolatileTest {
    public static int v = 0;

    public static void main(String[] args) throws InterruptedException {
        ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB();

        new Thread(threadA, "threadA").start();
        Thread.sleep(1000L);//为了保证threadA比threadB先启动，sleep一下
        new Thread(threadB, "threadB").start();

    }

    static class ThreadA extends Thread {
        @Override
        public void run() {
            while (true) {
                if (v == 1) {
                    System.out.println(Thread.currentThread().getName() + " : v is " + v);
                    break;
                }
            }
        }
    }

    static class ThreadB extends Thread {
        @Override
        public void run() {
            v++;
            System.out.println(Thread.currentThread().getName() + " : v is " + v);
        }
    }
}
