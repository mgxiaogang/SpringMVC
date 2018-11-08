package com.liupeng.dto;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author fengdao.lp
 * @Table 用此注解标明对应的数据库表名, 若无此配置默认是映射为 ConditionRecord->condition_record
 * @date 2018/2/8
 */
@Table(name = "user")
public class User extends BaseDO implements Serializable {

    private static final long serialVersionUID = 1286009320716849945L;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    public User() {
    }

    public User(String name) {
        this.name = name;
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (getId().compareTo(other.getId()) != 0) {
            return false;
        }
        if (StringUtils.isBlank(name)) {
            if (StringUtils.isNotBlank(other.name)) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (age != other.age) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((getId() == null) ? 0 : getId().hashCode());
        result = prime
                * result
                + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + age;
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
