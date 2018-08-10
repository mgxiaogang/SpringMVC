package com.liupeng.sharding.shardingjdbc.service;

import java.util.List;

import com.liupeng.sharding.shardingjdbc.entity.Teacher;

/**
 * 处理用户的Service
 *
 * @author liupeng
 */
public interface TeacherService {

    boolean insert(Teacher u);

    List<Teacher> findAll();

    List<Teacher> findByUserIds(List<Integer> ids);

    void transactionTestSucess();

    void transactionTestFailure() throws IllegalAccessException;

}  