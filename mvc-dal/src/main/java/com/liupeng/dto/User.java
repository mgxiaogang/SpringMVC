package com.liupeng.dto;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.common.base.Objects;

/**
 * @author fengdao.lp
 * @Table 用此注解标明对应的数据库表名, 若无此配置默认是映射为 ConditionRecord->condition_record
 * @date 2018/2/8
 */
@Table(name = "user")
public class User extends BaseDO {

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    public User() {}

    public User(String name) {this.name = name;}

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
            .add("name", name)
            .add("age", age)
            .toString();
    }
}
