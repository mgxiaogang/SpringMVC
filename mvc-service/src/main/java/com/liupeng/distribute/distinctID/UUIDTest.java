package com.liupeng.distribute.distinctID;

import java.util.UUID;

/**
 * @author fengdao.lp
 * @date 2018/8/29
 */
public class UUIDTest {
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
        System.out.println(UUID.randomUUID().version());

        System.out.println(UUID.nameUUIDFromBytes("890110866094329856".getBytes()).toString().replace("-", ""));
        System.out.println(UUID.nameUUIDFromBytes("890110866094329856".getBytes()).version());
    }
}
