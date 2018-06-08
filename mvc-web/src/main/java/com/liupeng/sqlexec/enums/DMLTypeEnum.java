package com.liupeng.sqlexec.enums;

public enum DMLTypeEnum {
    UNKNOW(0, "未知"),
    INSERT(1, "增"),
    DELETE(2, "删"),
    UPDATE(3, "改"),
    SELECT(4, "查");

    private int value;

    private String name;

    private DMLTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 获取 value  <br>
     *
     * @return value  <br>
     */
    public int getValue() {
        return value;
    }

    /**
     * 获取 name  <br>
     *
     * @return name  <br>
     */
    public String getName() {
        return name;
    }
}
