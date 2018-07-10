package com.liupeng.jdk1_8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author fengdao.lp
 * @date 2018/7/10
 */
public class Feature2_lambda {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        /*Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });*/
        Collections.sort(names, (a, b) -> b.compareTo(a));
    }
}
