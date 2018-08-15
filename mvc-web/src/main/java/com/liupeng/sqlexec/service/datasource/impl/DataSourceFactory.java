package com.liupeng.sqlexec.service.datasource.impl;

import com.liupeng.spring.beanfactory.CacheBeansFactory;
import com.liupeng.sqlexec.service.datasource.IDataSourceFactory;
import com.liupeng.sqlexec.service.datasource.IDataSourceSwitch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据源工厂实现类
 */
@Service
public class DataSourceFactory implements IDataSourceFactory {
    /*@Resource
    List<IDataSourceSwitch> dataSourceSwithers;

    @Override
    public IDataSourceSwitch getDateSourceSwitch(String dbName) {
        IDataSourceSwitch ret = null;
        for (IDataSourceSwitch dataSourceSwitch : dataSourceSwithers) {
            if (dataSourceSwitch.supports(dbName)) {
                ret = dataSourceSwitch;
                break;
            }
        }
        return ret;
    }*/

    @Autowired
    private CacheBeansFactory cacheBeansFactory;

    @Override
    public IDataSourceSwitch getDateSourceSwitch(String dbName) {
        IDataSourceSwitch ret = cacheBeansFactory.getBean(IDataSourceSwitch.class, dbName);
        return ret;
    }
}
