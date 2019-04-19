package com.github.springkit.mapper;

import com.github.springkit.Application;
import com.github.springkit.dao.StudentDao;
import com.github.springkit.po.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: alex
 * @Description:
 * @Date: created in 2019/4/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class StudentMapper_test {
    @Autowired
    private StudentDao studentDao;
    private Student student = new Student(1, "alex", 30);

    @Test
    public void add() {
        System.out.println(studentDao.add(student));
    }

    @Test
    public void update() {
        System.out.println(studentDao.update(student));
    }

    @Test
    public void queryStudentsListMap() {
        System.out.println(studentDao.queryStudentsListMap());
    }

    @Test
    public void queryStudentById() {
        System.out.println(studentDao.queryStudentById(2));
    }
}
