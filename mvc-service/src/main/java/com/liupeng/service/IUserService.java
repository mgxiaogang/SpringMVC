package com.liupeng.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.liupeng.dto.User;

/**
 * @author fengdao.lp
 * @date 2018/2/8
 */
public interface IUserService {
    List<User> queryAll();

    void insert(User user);

    User selectOne(User user);

    void deleteByPrimaryKey(Long l);

    PageInfo<User> selectByPage();
}
