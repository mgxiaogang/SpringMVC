package com.liupeng.sharding.shardingjdbc.mapper;

import java.util.List;

import com.liupeng.sharding.shardingjdbc.entity.Student;

/**
 * 处理学生的数据操作接口
 *
 * @author liupeng
 */
public interface StudentMapper {

    Integer insert(Student s);

    List<Student> findAll();

    List<Student> findByStudentIds(List<Integer> studentIds);

}  

