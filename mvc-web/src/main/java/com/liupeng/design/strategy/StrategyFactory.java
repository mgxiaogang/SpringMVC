package com.liupeng.design.strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略工厂 【可以考虑使用CacheBeanFactory来更好的封装】
 *
 * @author fengdao.lp
 * @date 2018/6/26
 */
public class StrategyFactory {
    private static StrategyFactory factory = new StrategyFactory();

    private StrategyFactory() {
    }

    private static Map<Integer, Strategy> strategyMap = new HashMap<>();

    static {
        strategyMap.put(TypeEnum.E_BANK.value(), new EBankStrategy());
        strategyMap.put(TypeEnum.MOBILE.value(), new MobileStrategy());
    }

    private Strategy creator(Integer type) {
        return strategyMap.get(type);
    }

    public static StrategyFactory getInstance() {
        return factory;
    }

    public static void main(String[] args) {
        // 网银充值100实际需要付多少钱
        Strategy strategy = StrategyFactory.getInstance().creator(1);
        System.out.println(strategy.getFinalMoney(100));

        // 手机充值100实际需要付多少钱
        Strategy strategy1 = StrategyFactory.getInstance().creator(2);
        System.out.println(strategy1.getFinalMoney(100));
    }
}
