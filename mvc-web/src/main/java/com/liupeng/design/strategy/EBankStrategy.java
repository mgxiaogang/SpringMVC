package com.liupeng.design.strategy;

/**
 * 网银充值
 *
 * @author fengdao.lp
 * @date 2018/6/26
 */
public class EBankStrategy implements Strategy {
    @Override
    public int getFinalMoney(int money) {
        return money * 3;
    }
}
