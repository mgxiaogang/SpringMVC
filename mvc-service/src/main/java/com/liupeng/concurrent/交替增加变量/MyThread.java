package com.liupeng.concurrent.交替增加变量;

/**
 * 多线程交替数数，从1数到10
 *
 * @author fengdao.lp
 * @date 2018/8/6
 */
public class MyThread implements Runnable {

    // 必须是static两个线程中才是同一个对象(或者再main函数里面通过构造器传入进来也是一个对象)
    static Business business = new Business();

    @Override
    public void run() {
        while (business.getN() <= 10) {
            try {
                business.shushu();
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new MyThread());
        Thread t2 = new Thread(new MyThread());
        t1.start();
        t2.start();
    }
}
