package com.github.springkit;

import com.github.springkit.datasource.DataSourceContextHolder;
import com.github.springkit.service.StudentService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.greaterThan;

/**
 * test.student.id  1~9
 * test1.student.id 10~99
 * test2.student.id  >=100
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Student_test {
    @Autowired
    private StudentService studentService;

    @Test
    public void go_getList() {
        Assert.assertThat((double) studentService.getStudent().getId(), closeTo(0, 9));
        Assert.assertThat((double) studentService.getStudentByDs1().getId(), closeTo(9, 99));
        Assert.assertThat(studentService.getStudentByDs2().getId(), greaterThan(99));
    }

    @Test
    public void go_useManual() {
        //aop比手动优先级高
        DataSourceContextHolder.setDataSourceType("ds1");
        Assert.assertThat((double) studentService.getStudentByDs2().getId(), closeTo(10, 99));
    }

    @Test
    public void go_embedInvoke() {
        //no aop
        Assert.assertThat((double) studentService.embedInvoke().getId(), closeTo(0, 9));
    }

    @Test
    public void go_embedInvoke_fixed() {
        Assert.assertThat((double) studentService.embedInvoke_fixed().getId(), closeTo(10, 99));
    }

    @Test
    public void go_addStudent_withTrans() {
        //添加@Transactional时，神奇的发现，他没有插入到test1,test2,而是到test
        studentService.addStudent();
    }

    @Ignore
    @Test
    public void go_addStudent_fixed() {
        //不用使用@Transactional
        studentService.addStudent_fixed();
    }

    @Ignore
    @Test
    public void go_addStudentManualTransaction() {
        //其实使用jdbc也不能完全做到(第二个失败，第一个还是会成功)，这样做意义不大，本身是两个独立的数据单元
        studentService.addStudentManualTransaction();
    }

}
