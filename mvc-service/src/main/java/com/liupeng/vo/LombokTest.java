package com.liupeng.vo;

import java.util.Date;

import lombok.Data;

/**
 * lombok测试类
 *
 * @author fengdao.lp
 * @date 2018/6/6
 */
@Data
public class LombokTest {
    private int id;

    private String name;

    private String password;

    private Date birthday;

    public static void main(String[] args) {
        LombokTest lombokTest = new LombokTest();
        lombokTest.setName("liupeng");
        System.out.println(lombokTest.getName());
    }
}