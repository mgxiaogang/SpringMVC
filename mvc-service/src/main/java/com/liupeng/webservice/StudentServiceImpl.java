package com.liupeng.webservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.liupeng.vo.Student;

/**
 * @author fengdao.lp
 * @date 2018/7/19
 */
public class StudentServiceImpl implements StudentService {
    @Override
    public Student queryStudent(long id) {
        // 使用静态数据
        Student student = new Student();
        student.setId(id);
        student.setName("张三");
        student.setDate(new Date());
        return student;
    }

    @Override
    public List<Student> queryStudentList(String type) {
        // 使用静态数据
        List<Student> list = new ArrayList<Student>();
        Student student1 = new Student();
        student1.setId(1000L);
        student1.setName("张三");
        student1.setDate(new Date());

        Student student2 = new Student();
        student2.setId(1000L);
        student2.setName("张三");
        student2.setDate(new Date());

        list.add(student1);
        list.add(student2);
        return list;
    }
}
