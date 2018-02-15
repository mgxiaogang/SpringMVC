package com.liupeng.dao.user;

import java.util.List;

import com.liupeng.BaseMapper;
import com.liupeng.dto.User;

public interface UserMapper extends BaseMapper<User> {
    List<User> queryPerson();
}
