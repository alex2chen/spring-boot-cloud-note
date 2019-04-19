package com.kxtx.boot.repository;

import com.kxtx.boot.model.Employee;
import com.kxtx.boot.model.SexEnum;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/21
 */
//@Mapper
public interface EmployeeMapper {
    @Select("SELECT * FROM employee")
    @Results({
            @Result(property = "email", column = "email"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "gender", column = "gender", javaType = SexEnum.class),
    })
    List<Employee> queryAll();

    @Select("SELECT * FROM employee where empid>=#{id} LIMIT #{_pagesize} OFFSET #{_skiprows}")
    @Results({
            @Result(property = "email", column = "email"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "gender", column = "gender", javaType = SexEnum.class),
    })
    List<Employee> queryByPage(HashMap parameter);

    @Select("SELECT COUNT(*) FROM employee WHERE empid>=#{id}")
    Integer queryCount(Map parameter);

    @Select("SELECT * FROM employee WHERE empid = #{id}")
    Employee getOne(Long id);

    @Insert("INSERT INTO employee(email,first_name,last_name,gender) VALUES(#{email},#{firstName},#{lastName},#{gender})")
    int insert(Employee employee);

    @Update("UPDATE employee SET first_name=#{firstName},last_name=#{lastName} WHERE empid =#{empId}")
    void update(Employee employee);

    @Delete("DELETE FROM employee WHERE empid =#{id}")
    void delete(Long id);

}
