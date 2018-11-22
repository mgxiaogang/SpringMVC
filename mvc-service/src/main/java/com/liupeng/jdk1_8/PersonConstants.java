package com.liupeng.jdk1_8;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 人员配置常量类
 */
public interface PersonConstants {

    /**
     * 处理人配置
     */
    interface DefaultHandlerMan {
        // 默认投诉工单处理人
        String DEFAULT_COMPLAINT_HANDLER_MAN = "defaultComplaintHandlerMan";

        // 默认会员留言工单处理人
        String DEFAULT_MEMBERMESSAGE_HANDLER_MAN = "defaultMemberMessageHandlerMan";

        // 默认订单工单处理人
        String DEFAULT_ORDER_HANDLER_MAN = "defaultOrderWorkorderHandlerMan";

        // jdk1.8开始支持非抽象的方法实现 返回默认处理人key集合
        static List<String> getDefaultHandlerManKey() {
            List<String> defaultHandlerMans = Lists.newArrayList();
            Field[] fields = PersonConstants.DefaultHandlerMan.class.getDeclaredFields();
            for (Field field : fields) {
                try {
                    defaultHandlerMans.add((String) field.get(field.getName()));
                } catch (IllegalAccessException e) {
                    return null;
                }
            }
            return defaultHandlerMans;
        }
    }
}
