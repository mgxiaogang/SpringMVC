package com.liupeng.spring.dynamicdatasource;

/**
 * DB数据源
 *
 * @author fengdao.lp
 * @date 2018/1/2
 */
public enum DbEnum {
    /**
     * 未知类型
     */
    UNKNOW(0, "未知类型"),

    /**
     * 本地数据源
     */
    WK_DATA_SOURCE(1, "myDataSource"),

    /**
     * 其他数据源
     */
    WKX_DATA_SOURCE(2, "otherDataSource");

    private int value;

    private String name;

    DbEnum(int i, String n) {
        this.value = i;
        this.name = n;
    }

    public static DbEnum valueOf(int value) {
        DbEnum[] rts = DbEnum.values();
        for (DbEnum rt : rts) {
            if (rt.value == value) {
                return rt;
            }
        }
        return UNKNOW;
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
