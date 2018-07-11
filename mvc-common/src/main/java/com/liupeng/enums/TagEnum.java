package com.liupeng.enums;

import com.liupeng.util.StandardEnum;

/**
 * 标签分组枚举
 *
 * @author fengdao.lp
 * @date 2018/7/11
 */
public interface TagEnum {
    enum BizTag implements StandardEnum {
        @EnumTag(1)
        TAG_A(1, "标签1"),

        @EnumTag(1)
        @EnumTag(2)
        TAG_B(2, "标签2");

        private int value;
        private String desc;

        BizTag(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        @Override
        public int getValue() {
            return value;
        }

        @Override
        public String getDesc() {
            return desc;
        }

        public static BizTag valueOf(int value) {
            return StandardEnum.valueOf(BizTag.class, value);
        }
    }

    enum SystemTag implements StandardEnum {
        @EnumTag(1)
        TAG_A(1, "标签1"),

        @EnumTag(1)
        @EnumTag(2)
        TAG_B(2, "标签2");

        private int value;
        private String desc;

        SystemTag(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        @Override
        public int getValue() {
            return value;
        }

        @Override
        public String getDesc() {
            return desc;
        }

        public static SystemTag valueOf(int value) {
            return StandardEnum.valueOf(SystemTag.class, value);
        }
    }
}
