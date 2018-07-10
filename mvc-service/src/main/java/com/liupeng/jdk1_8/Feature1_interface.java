package com.liupeng.jdk1_8;

/**
 * 接口可以添加非抽象的方法实现
 *
 * @author fengdao.lp
 * @date 2018/7/10
 */
public interface Feature1_interface {
    double calculate(int a);

    /**
     * 非抽象的方法实现
     */
    default double sqrt(int a) {
        return Math.sqrt(a);
    }

    static void main(String[] args) {
        Feature1_interface feature1_interface = new Feature1_interface() {
            @Override
            public double calculate(int a) {
                return a * 100;
            }
        };
        System.out.println(feature1_interface.calculate(10));
        System.out.println(feature1_interface.calculate(20));
    }
}
