package com.liupeng.sqlexec.service.datasource.impl;

import com.liupeng.sqlexec.service.datasource.IDataSourceSwitch;
import org.springframework.stereotype.Service;

/**
 * 1.数据源为采购系统put_nm
 * 2.切换为采购系统主库
 */
@Service
public class PurNmDataSourceSwitchImpl implements IDataSourceSwitch {

    private final String dbName = "master";

    private final String dbNameExternal = "pur_nm";

    @Override
    public void switchDataSource() {
        // 适用原有代码切换到资源主库
        //if (!dbName.equals(DataSourceSwitch.getDataSouce())) {
        //    DataSourceSwitch.setMaster();
        //}
    }

    @Override
    public boolean supports(Object object) {
        boolean ret = false;
        if (object instanceof String) {
            if (dbNameExternal.equals(object)) {
                ret = true;
            }
        }
        return ret;
    }

    /**
     * 人员操作权限，可在此处扩展
     */
    @Override
    public boolean checkManagerValid(int uid) {
        return true;
    }
}
