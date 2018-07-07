package com.liupeng.dao.user;

import java.util.List;
import java.util.Map;

import com.liupeng.BaseMapper;
import com.liupeng.dto.User;
import org.apache.ibatis.session.RowBounds;

public interface UserMapper extends BaseMapper<User> {
    List<User> queryPerson();

    List<User> queryByPage(Map<String, Integer> map);

    List<User> queryByRowBounds(Map<String, Integer> map, RowBounds rowBounds);

    List<User> queryByPageParam(Map<String, Object> map);
}
