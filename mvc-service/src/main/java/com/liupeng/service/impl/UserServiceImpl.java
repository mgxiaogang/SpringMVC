package com.liupeng.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liupeng.dao.user.UserMapper;
import com.liupeng.dto.User;
import com.liupeng.service.BaseService;
import com.liupeng.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fengdao.lp
 * @date 2018/2/8
 */
@Service
public class UserServiceImpl extends BaseService<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> queryAll() {
        PageHelper.startPage(1, 2);
        List<User> list = userMapper.queryPerson();
        PageInfo<User> pageInfo = new PageInfo<>(list);
        return pageInfo.getList();
    }

}
