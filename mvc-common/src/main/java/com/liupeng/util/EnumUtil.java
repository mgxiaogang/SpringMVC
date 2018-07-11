package com.liupeng.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import com.liupeng.enums.EnumTag;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * 枚举工具类
 *
 * @author fengdao.lp
 * @date 2018/7/11
 */
public class EnumUtil<T extends Enum<T> & StandardEnum> implements InitializingBean {
    private Map<String, Class<T>> enumTypes;
    private Map<String, Map<String, String>> enumLists = Maps.newHashMap();

    /**
     * 根据key获取枚举
     *
     * @param key xml中配置的key值
     * @return 对应枚举
     */
    public Map<String, String> getEnumWithKey(String key) {
        Map<String, String> maps = enumLists.get(key);
        if (null == maps) {
            Class<T> clazz = enumTypes.get(key);
            if (null != clazz) {
                maps = buildMapWithEnum(clazz, null);
                enumLists.putIfAbsent(key, maps);
            }
        }
        return maps;
    }

    /**
     * 根据key获取枚举
     *
     * @param key      xml中配置的key值
     * @param tagValue 注解值
     * @return 对应枚举
     */
    public Map<String, String> getEnumWithKeyAndTagValue(String key, Integer tagValue) {
        if (tagValue == null) {
            return getEnumWithKey(key);
        }

        String finalKey = String.format("%s-%s", key, tagValue);
        Map<String, String> maps = enumLists.get(finalKey);
        if (null == maps) {
            Class<T> clazz = enumTypes.get(key);
            if (null != clazz) {
                maps = buildMapWithEnum(clazz, tagValue);
                enumLists.putIfAbsent(key, maps);
            }
        }
        return maps;
    }

    private Map<String, String> buildMapWithEnum(Class<T> clazz, Integer tagValue) {
        T[] constants = clazz.getEnumConstants();
        Map<String, String> result = new LinkedHashMap<>(constants.length);
        for (T constant : constants) {
            try {
                EnumTag[] tags = constant.getClass().getField(constant.name()).getAnnotationsByType(EnumTag.class);
                if (hasTag(tags, tagValue)) {
                    result.put(String.valueOf(constant.getValue()), String.valueOf(constant.getDesc()));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return Collections.unmodifiableMap(result);
    }

    private boolean hasTag(EnumTag[] tags, Integer tagValue) {
        if (tagValue == null) {
            return true;
        }
        if (tags == null || tags.length == 0) {
            return false;
        }
        for (EnumTag tag : tags) {
            if (tag.value() == tagValue) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(enumTypes, "enumTypes should not be empty");
    }

    public Map<String, Class<T>> getEnumTypes() {
        return enumTypes;
    }

    public void setEnumTypes(Map<String, Class<T>> enumTypes) {
        this.enumTypes = enumTypes;
    }
}
