package com.liupeng.domain;

import com.liupeng.validator.annotation.CollectionNotHasNullElement;
import com.liupeng.validator.group.CommonGroups;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author fengdao.lp
 * @date 2018/5/24
 */
public class UserVO {

    private int age;

    @NotBlank(groups = CommonGroups.Save.class, message = "名称不能为空")
    private String name;

    private Date date;

    @NotNull(groups = {CommonGroups.Save.class, CommonGroups.Update.class}, message = "新增、修改时ids不能为空")
    @CollectionNotHasNullElement.List({
            @CollectionNotHasNullElement(groups = {CommonGroups.Save.class}, message = "新增时ids不能为空"),
            @CollectionNotHasNullElement(groups = {CommonGroups.Update.class}, message = "修改时ids不能为空")
    })
    private List<Integer> ids;

    public UserVO() {
    }

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

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", ids=" + ids +
                '}';
    }
}
