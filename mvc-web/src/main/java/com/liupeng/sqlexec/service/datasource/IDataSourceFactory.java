package com.liupeng.sqlexec.service.datasource;

/**
 * 数据源工厂
 */
public interface IDataSourceFactory {
    IDataSourceSwitch getDateSourceSwitch(String daName);
}