package com.liupeng.util;

import java.util.List;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

/**
 * @author fengdao.lp
 * @date 2018/7/11
 */
public class CollectionUtil {
    /**
     * 类型转换
     */
    public static void stringtoInteger() {
        List<String> list = Lists.newArrayList("1", "2");
        List<Integer> result = Lists.newArrayList();
        CollectionUtils.collect(list, new Transformer() {
            @Override
            public Object transform(Object input) {
                return Integer.parseInt((String)input);
            }
        }, result);
        System.out.println(result);
    }

    public static void main(String[] args) {
        stringtoInteger();
    }
}
