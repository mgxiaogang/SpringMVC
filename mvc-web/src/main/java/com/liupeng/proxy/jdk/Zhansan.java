package com.liupeng.proxy.jdk;

/**
 * @author fengdao.lp
 * @date 2018/7/2
 */
public class Zhansan implements People{
    @Override
    public void eat() throws Exception {
        System.out.println("张三吃饭时喜欢看美女");
    }
}
