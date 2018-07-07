package com.liupeng.service.impl;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.liupeng.dao.user.UserMapper;
import com.liupeng.dto.User;
import com.liupeng.service.IDemoService;
import com.liupeng.spring.dynamicdatasource.DataSource;
import com.liupeng.spring.dynamicdatasource.DbEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fengdao.lp
 * @date 2018/7/5
 */
@Service
public class DemoServiceImpl implements IDemoService {
    @Autowired
    private UserMapper userMapper;

    @DataSource(value = DbEnum.WK_DATA_SOURCE)
    @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
    @Override
    public void say(String str) {
        Map<String, Integer> map = Maps.newHashMap();
        map.put("currentPage", 1);
        map.put("pageSize", 5);
        List<User> list = userMapper.queryByPage(map);
        System.out.println(str + ",test");
    }
}
