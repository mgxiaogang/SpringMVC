package com.liupeng.dto;

import com.google.common.base.Objects;

/**
 * @author fengdao.lp
 * @date 2018/2/8
 */
public class User {
    private int id;
    private String name;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this.getClass())
            .add("id", id)
            .add("name", name)
            .add("age", age)
            .toString();
    }
}
