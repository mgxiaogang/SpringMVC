package com.liupeng.jdk1_8.functionInterface;

/**
 * 函数式接口demo
 * <p>
 * FunctionalInterface注解标注的接口是函数式接口(如果一个接口满足函数式接口的定义会默认转成函数式接口，但是最好使用注解显示声明，因为函数式接口比较脆弱，如果开发无意中新增了其他方法，就破坏了函数式接口的要求)
 * </p>
 *
 * @author liupeng
 * @Date 2019/2/12
 */
@FunctionalInterface
public interface FunctionInterfaceTest {
    /**
     * 要求有且仅有一个抽象方法(任何被Object实现的方法都不能当做抽象方法)
     */
    String getValue(String str);

    /**
     * 但是可以有多个实例方法(非抽象的方法实现)
     */
    default void run() {
        System.out.println("run");
    }
}

class Test {
    public static String getValue(String str, FunctionInterfaceTest test) {
        return test.getValue(str);
    }

    public static void main(String[] args) {
        /**
         * 第二个参数和下面等价
         * <pre>
         * new FunctionInterfaceTest() {
         *      @Override
         *      public String getValue(String str) {
         *          return str.toUpperCase();
         *      }
         * }
         * </pre>
         */
        String str = getValue("hello world", String::toUpperCase);
        System.out.println(str);
    }
}
