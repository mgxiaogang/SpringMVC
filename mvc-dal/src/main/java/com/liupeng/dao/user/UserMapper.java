package com.liupeng.dao.user;

import java.util.List;
import java.util.Map;

import com.liupeng.BaseMapper;
import com.liupeng.dto.User;

public interface UserMapper extends BaseMapper<User> {
    List<User> queryPerson();

    List<User> queryByPage(Map<String, Integer> map);
}
