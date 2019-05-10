package com.github.springkit.service.support;

import com.github.springkit.datasource.ChooseDataSource;
import com.github.springkit.datasource.DataSourceContextHolder;
import com.github.springkit.datasource.DataSourceKey;
import com.github.springkit.datasource.TransactionTemplateUtils;
import com.github.springkit.datasource.support.DynamicDataSourceRouteImpl;
import com.github.springkit.entity.Student;
import com.github.springkit.entity.StudentExample;
import com.github.springkit.mapper.StudentMapper;
import com.github.springkit.mapper.UsersMapper;
import com.github.springkit.service.StudentService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Student getStudent() {
        StudentExample example = new StudentExample();
        usersMapper.deleteByPrimaryKey(55);
        return selectOne(studentMapper.selectByExample(example));
    }

    @Override
    @ChooseDataSource(DataSourceKey.DS1_MASTER)
    public Student getStudentByDs1() {
        StudentExample example = new StudentExample();
        return selectOne(studentMapper.selectByExample(example));
    }

    @Override
    @ChooseDataSource(DataSourceKey.DS2_MASTER)
    public Student getStudentByDs2() {
        StudentExample example = new StudentExample();
        return selectOne(studentMapper.selectByExample(example));
    }

    private Student selectOne(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return null;
        }
        return students.get(0);
    }

    @Override
    public Student embedInvoke() {
        return getStudentByDs1();
    }

    @Override
    public Student embedInvoke_fixed() {
        if (null != AopContext.currentProxy()) {
            return ((StudentService) AopContext.currentProxy()).getStudentByDs1();
        }
        return null;
    }
    @Override
//    @Transactional
    public void addStudent() {
        Student student = new Student();
        student.setId(13);
        student.setName("top1");
        student.setAge(20);
        DataSourceContextHolder.setDataSourceType(DataSourceKey.DS1_MASTER);
        studentMapper.insert(student);
        student.setId(103);
        student.setName("top2");
        DataSourceContextHolder.setDataSourceType(DataSourceKey.DS2_MASTER);
        studentMapper.insert(student);
    }
    @Transactional
    @ChooseDataSource(DataSourceKey.DS1_MASTER)
    public void insert_ds1(Student student){
        studentMapper.insert(student);
    }
    @ChooseDataSource(DataSourceKey.DS2_MASTER)
    public void insert_ds2(Student student){
        studentMapper.insert(student);
    }
    @Override
//    @Transactional
    public void addStudent_fixed(){
        Student student = new Student();
        student.setId(13);
        student.setName("top1");
        student.setAge(20);
        if (null != AopContext.currentProxy()) {
             ((StudentServiceImpl) AopContext.currentProxy()).insert_ds1(student);
        }
        student.setId(103);
        student.setName("top2");
        if (null != AopContext.currentProxy()) {
            ((StudentServiceImpl) AopContext.currentProxy()).insert_ds2(student);
        }
    }

    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private DynamicDataSourceRouteImpl dynamicDataSource;

    @Override
    public void addStudentManualTransaction() {
//        DynamicDataSourceContextHolder.setDataSourceType(DataSourceConfigMappings.DS2_MASTER);
//        transactionManager.setDataSource(dynamicDataSource.getdDynamicDataSource());
        final TransactionTemplate txTemplate = TransactionTemplateUtils.getDefaultTransactionTemplate(transactionManager);
        final JdbcTemplate jdbcTemplate1=new JdbcTemplate(dynamicDataSource.getDataSourceByKey(DataSourceKey.DS1_MASTER));
        final JdbcTemplate jdbcTemplate2=new JdbcTemplate(dynamicDataSource.getDataSourceByKey(DataSourceKey.DS2_MASTER));
        //transactionManager.setDataSource();
        txTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    String sql="insert into student(id,name,age) values (?,?,?)";
                    jdbcTemplate1.update(sql,13,"top1","20");
                    jdbcTemplate2.update(sql,103,"top1","20");
                } catch (Exception e) {
                    throw e;
                }
            }
        });
    }
}
