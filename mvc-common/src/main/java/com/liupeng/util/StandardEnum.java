package com.liupeng.util;

/**
 * 枚举的基础接口
 *
 * @author ethanzhou
 * @date 2018-01-24
 */
public interface StandardEnum {

    /**
     * 获取value
     */
    int getValue();

    /**
     * 获取描述
     */
    String getDesc();

    /**
     * 通用方法, 根据value转换为枚举
     */
    static <T extends Enum<T> & StandardEnum> T valueOf(Class<T> type, Integer value) {
        if (value == null) {
            return null;
        }

        for (T t : type.getEnumConstants()) {
            if (t.getValue() == value) {
                return t;
            }
        }

        return null;
    }
}
