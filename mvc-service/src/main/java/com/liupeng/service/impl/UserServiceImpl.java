package com.liupeng.service.impl;

import javax.annotation.Resource;

import com.liupeng.dao.user.UserMapper;
import com.liupeng.dto.User;
import com.liupeng.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author fengdao.lp
 * @date 2018/2/8
 */
@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User queryAll() {
        return userMapper.queryPerson();
    }
}
