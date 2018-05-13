package com.liupeng.dto;

import javax.persistence.Transient;

/**
 * @author shengji.zk
 */
public class PageQuery {
    // 如果你的实体类中包含了不是数据库表中的字段，你需要给这个字段加上@Transient注解，这样通用Mapper在处理单表操作时就不会将标注的属性当成表字段处理！
    @Transient
    private int page = 1;
    @Transient
    private int size = 20;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
