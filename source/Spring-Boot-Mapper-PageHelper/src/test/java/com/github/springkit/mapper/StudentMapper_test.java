package com.github.springkit.mapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.springkit.Application;
import com.github.springkit.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: alex
 * @Description:
 * @Date: created in 2019/4/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class StudentMapper_test {
    @Autowired
    private StudentMapper studentMapper;
    private Student student = new Student( "alex", 30);

    @Test
    public void add() {
        System.out.println(studentMapper.insert(student));
    }

    @Test
    public void query() {
        Example example = new Example(Student.class);
        example.createCriteria().andCondition("name like '%i%'");
        example.setOrderByClause("id desc");
        List<Student> userList = this.studentMapper.selectByExample(example);
        for (Student u : userList) {
            System.out.println(u.getName());
        }
    }

    @Test
    public void queryByPage() {
        PageHelper.startPage(2, 2);
        List<Student> list = studentMapper.selectAll();
        PageInfo<Student> pageInfo = new PageInfo<Student>(list);
        List<Student> result = pageInfo.getList();
        for (Student u : result) {
            System.out.println(u.getName());
        }
    }
}
