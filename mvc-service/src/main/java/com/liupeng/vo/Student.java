package com.liupeng.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author fengdao.lp
 * @date 2018/7/19
 */
@XmlRootElement
public class Student {
    private long id;
    private String name;
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
