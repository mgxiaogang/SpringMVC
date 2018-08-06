package com.liupeng.concurrent.交替增加变量;

/**
 * @author fengdao.lp
 * @date 2018/8/6
 */
public class Business {
    int n = 1;

    /**
     * 数数，必须加同步
     */
    public synchronized void shushu() {
        try {
            System.out.println("线程" + Thread.currentThread().getName() + "说：" + n);
            n++;
            // 唤醒处于等待状态的线程
            this.notify();
            // 进入等待状态
            this.wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getN() {
        return n;
    }
}
