package com.liupeng.domain;

/**
 * @author fengdao.lp
 * @date 2018/5/24
 */
public class UserVO {
    private int age;
    private String name;

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

    @Override
    public String toString() {
        return "UserVO{" +
            "age=" + age +
            ", name='" + name + '\'' +
            '}';
    }
}
