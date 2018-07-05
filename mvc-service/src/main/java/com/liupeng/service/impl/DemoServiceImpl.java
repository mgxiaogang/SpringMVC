package com.liupeng.service.impl;

import com.liupeng.service.IDemoService;
import com.liupeng.spring.dynamicdatasource.DataSource;
import com.liupeng.spring.dynamicdatasource.DbEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fengdao.lp
 * @date 2018/7/5
 */
@Service
public class DemoServiceImpl implements IDemoService {

    @DataSource(value = DbEnum.WK_DATA_SOURCE)
    @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
    @Override
    public void say(String str) {
        System.out.println(str + ",test");
    }
}
