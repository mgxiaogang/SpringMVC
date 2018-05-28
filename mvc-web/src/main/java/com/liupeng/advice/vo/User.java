package com.liupeng.advice.vo;

import com.liupeng.advice.annotation.UserNickRender;

/**
 * @author fengdao.lp
 * @date 2018/3/13
 */
public class User {

    private int id;

    @UserNickRender
    private String rootId;

    @UserNickRender(aliasField = "userNameField")
    private String name;

    @UserNickRender(aliasField = "userAgeField")
    private String age;

    @UserNickRender(aliasField = "userHeightField")
    private String height;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", rootId='" + rootId + '\'' +
            ", name='" + name + '\'' +
            ", age='" + age + '\'' +
            ", height='" + height + '\'' +
            '}';
    }
}
