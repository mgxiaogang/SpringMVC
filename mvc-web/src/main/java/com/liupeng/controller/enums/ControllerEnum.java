package com.liupeng.controller.enums;

/**
 * @author fengdao.lp
 * @date 2018/5/24
 */
public enum ControllerEnum {
    TEST1(1, "测试1"),

    TEST2(2, "测试2");

    private int value;

    private String name;

    ControllerEnum(int i, String n) {
        this.value = i;
        this.name = n;
    }

    public static ControllerEnum valueOf(int value) {
        ControllerEnum[] rts = ControllerEnum.values();
        for (ControllerEnum rt : rts) {
            if (rt.value == value) {
                return rt;
            }
        }
        return TEST1;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
