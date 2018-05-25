package com.liupeng.domain;

import java.util.Date;

/**
 * @author fengdao.lp
 * @date 2018/5/24
 */
public class UserVO {
    private int age;

    private String name;

    private Date date;

    public UserVO() {}

    public UserVO(int age, String name, Date date) {
        this.age = age;
        this.name = name;
        this.date = date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserVO{" +
            "age=" + age +
            ", name='" + name + '\'' +
            ", date=" + date +
            '}';
    }
}
