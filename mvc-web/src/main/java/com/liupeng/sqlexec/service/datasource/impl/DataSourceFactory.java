package com.liupeng.sqlexec.service.datasource.impl;

import java.util.List;

import javax.annotation.Resource;

import com.liupeng.sqlexec.service.datasource.IDataSourceFactory;
import com.liupeng.sqlexec.service.datasource.IDataSourceSwitch;
import org.springframework.stereotype.Service;

/**
 * 数据源工厂实现类
 */
@Service
public class DataSourceFactory implements IDataSourceFactory {
    @Resource
    List<IDataSourceSwitch> dataSourceSwithers;

    @Override
    public IDataSourceSwitch getDateSourceSwitch(String daName) {
        IDataSourceSwitch ret = null;
        for (IDataSourceSwitch dataSourceSwitch : dataSourceSwithers) {
            if (dataSourceSwitch.supports(daName)) {
                ret = dataSourceSwitch;
                break;
            }
        }
        return ret;
    }
}
