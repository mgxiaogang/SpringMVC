package com.liupeng.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;

/**
 * @author fengdao.lp
 * @date 2018/2/15
 */
public class BaseDO extends PageQuery {
    /**
     * 主键
     */
    @Id
    @OrderBy(value = "DESC")
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseDO{" +
            "id=" + id +
            '}';
    }
}
