package com.liupeng.design.strategy;

/**
 * 手机充值
 *
 * @author fengdao.lp
 * @date 2018/6/26
 */
public class MobileStrategy implements Strategy {
    @Override
    public int getFinalMoney(int money) {
        return money * 2;
    }
}
