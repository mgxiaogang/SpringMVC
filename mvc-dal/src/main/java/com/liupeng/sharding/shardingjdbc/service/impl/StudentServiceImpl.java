package com.liupeng.sharding.shardingjdbc.service.impl;

import javax.annotation.Resource;

import com.liupeng.sharding.shardingjdbc.entity.Student;
import com.liupeng.sharding.shardingjdbc.mapper.StudentMapper;
import com.liupeng.sharding.shardingjdbc.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    public StudentMapper studentMapper;

    @Override
    public boolean insert(Student student) {
        return studentMapper.insert(student) > 0 ? true : false;
    }

}  