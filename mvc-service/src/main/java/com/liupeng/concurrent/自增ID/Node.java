package com.liupeng.concurrent.自增ID;

/**
 * @author fengdao.lp
 * @date 2018/8/4
 */

public class Node {
    private int i = 0;

    public int getI() {
        return i;
    }

    public synchronized void add() {
        i++;
    }
}
