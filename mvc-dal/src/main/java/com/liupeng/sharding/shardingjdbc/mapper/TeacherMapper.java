package com.liupeng.sharding.shardingjdbc.mapper;

import java.util.List;

import com.liupeng.sharding.shardingjdbc.entity.Teacher;

/**
 * 处理用户的数据操作接口
 *
 * @author liupeng
 */
public interface TeacherMapper {

    Integer insert(Teacher u);

    List<Teacher> findAll();

    List<Teacher> findByUserIds(List<Integer> userIds);

}  