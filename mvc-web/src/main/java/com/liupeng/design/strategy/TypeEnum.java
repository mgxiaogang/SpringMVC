package com.liupeng.design.strategy;

/**
 * @author fengdao.lp
 * @date 2018/6/26
 */
public enum TypeEnum {
    E_BANK(1, "网银"),
    MOBILE(2, "手机充值");
    /**
     * 状态值
     */
    private int value;

    /**
     * 类型描述
     */
    private String description;

    private TypeEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int value() {
        return value;
    }

    public String description() {
        return description;
    }

    public static TypeEnum valueOf(int value) {
        for (TypeEnum type : TypeEnum.values()) {
            if (type.value() == value) {
                return type;
            }
        }
        return null;
    }
}
