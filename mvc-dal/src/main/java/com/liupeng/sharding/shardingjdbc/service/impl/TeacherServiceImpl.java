package com.liupeng.sharding.shardingjdbc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.liupeng.sharding.shardingjdbc.entity.Student;
import com.liupeng.sharding.shardingjdbc.entity.Teacher;
import com.liupeng.sharding.shardingjdbc.mapper.StudentMapper;
import com.liupeng.sharding.shardingjdbc.mapper.TeacherMapper;
import com.liupeng.sharding.shardingjdbc.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    @Resource
    public TeacherMapper teacherMapper;

    @Resource
    public StudentMapper studentMapper;

    @Override
    public boolean insert(Teacher u) {
        return teacherMapper.insert(u) > 0 ? true : false;
    }

    @Override
    public List<Teacher> findAll() {
        return teacherMapper.findAll();
    }

    @Override
    public List<Teacher> findByUserIds(List<Integer> ids) {
        return teacherMapper.findByUserIds(ids);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void transactionTestSucess() {
        Teacher u = new Teacher();
        u.setUserId(13);
        u.setAge(25);
        u.setName("war3 1.27");
        teacherMapper.insert(u);

        Student student = new Student();
        student.setStudentId(21);
        student.setAge(21);
        student.setName("hehe");
        studentMapper.insert(student);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void transactionTestFailure() throws IllegalAccessException {
        Teacher u = new Teacher();
        u.setUserId(13);
        u.setAge(25);
        u.setName("war3 1.27 good");
        teacherMapper.insert(u);

        Student student = new Student();
        student.setStudentId(21);
        student.setAge(21);
        student.setName("hehe1");
        studentMapper.insert(student);
        throw new IllegalAccessException();
    }

}  