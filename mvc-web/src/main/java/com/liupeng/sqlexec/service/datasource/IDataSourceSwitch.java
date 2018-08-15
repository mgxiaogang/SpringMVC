package com.liupeng.sqlexec.service.datasource;

import com.liupeng.spring.beanfactory.Supportable;

/**
 * 切换数据源
 *
 * @author 刘鹏
 */
public interface IDataSourceSwitch extends Supportable<String> {
    /**
     * 切换数据源
     */
    void switchDataSource();

    /**
     * 校验当前操作人是否有权限
     */
    boolean checkManagerValid(int uid);
}
