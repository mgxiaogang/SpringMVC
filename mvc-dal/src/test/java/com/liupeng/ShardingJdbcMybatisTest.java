package com.liupeng;

import java.util.List;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.liupeng.sharding.shardingjdbc.entity.Student;
import com.liupeng.sharding.shardingjdbc.entity.Teacher;
import com.liupeng.sharding.shardingjdbc.service.StudentService;
import com.liupeng.sharding.shardingjdbc.service.TeacherService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试分库分表规则
 *
 * @author fengdao.lp
 * @date 2018/8/9
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:sharding/shardingjdbc/shardingjdbc-datasource.xml",
    "classpath:sharding/shardingjdbc/shardingjdbc-spring.xml",})
public class ShardingJdbcMybatisTest {

    @Resource
    private TeacherService teacherService;

    @Resource
    private StudentService studentService;

    /**
     * After database sharding t_user result: [sharding_0]
     * After table sharding t_user result: [t_user_02]
     */
    @Test
    public void testTeacherInsert() {
        Teacher teacher = new Teacher();
        teacher.setUserId(2);
        teacher.setAge(26);
        teacher.setName("liupeng");
        Assert.assertEquals(teacherService.insert(teacher), true);
    }

    /**
     * After database sharding t_student result: [sharding_1]
     * After table sharding t_student result: [t_student_01]
     */
    @Test
    public void testStudentInsert() {
        Student student = new Student();
        student.setStudentId(1);
        student.setAge(28);
        student.setName("帆来");
        Assert.assertEquals(studentService.insert(student), true);
    }

    /**
     * 全库全表查
     * route sql to db: [sharding_0] sql: [SELECT id, user_id, name, age FROM t_user_00]
     * route sql to db: [sharding_0] sql: [SELECT id, user_id, name, age FROM t_user_01]
     * route sql to db: [sharding_0] sql: [SELECT id, user_id, name, age FROM t_user_02]
     * route sql to db: [sharding_1] sql: [SELECT id, user_id, name, age FROM t_user_00]
     * route sql to db: [sharding_1] sql: [SELECT id, user_id, name, age FROM t_user_01]
     * route sql to db: [sharding_1] sql: [SELECT id, user_id, name, age FROM t_user_02]
     */
    @Test
    public void testFindAll() {
        List<Teacher> users = teacherService.findAll();
        if (null != users && !users.isEmpty()) {
            for (Teacher u : users) {
                System.out.println(u);
            }
        }
    }

    /**
     * route sql to db: [sharding_1] sql: [SELECT id, user_id, name, age FROM t_user_01 WHERE user_id IN (?)]
     */
    @Test
    public void testSQLIN() {
        List<Teacher> users = teacherService.findByUserIds(Lists.newArrayList(1, 2));
        if (null != users && !users.isEmpty()) {
            for (Teacher u : users) {
                System.out.println(u);
            }
        }
    }

    @Test
    public void testTransactionTestSucess() {
        teacherService.transactionTestSucess();
    }

    @Test(expected = IllegalAccessException.class)
    public void testTransactionTestFailure() throws IllegalAccessException {
        teacherService.transactionTestFailure();
    }

}
