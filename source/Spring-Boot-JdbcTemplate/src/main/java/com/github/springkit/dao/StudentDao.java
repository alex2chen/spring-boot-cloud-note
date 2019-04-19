package com.github.springkit.dao;

import com.github.springkit.po.Student;

import java.util.List;
import java.util.Map;


public interface StudentDao {
	int add(Student student);
    int update(Student student);
    List<Map<String,Object>> queryStudentsListMap();
    Student queryStudentById(Integer id);
}
