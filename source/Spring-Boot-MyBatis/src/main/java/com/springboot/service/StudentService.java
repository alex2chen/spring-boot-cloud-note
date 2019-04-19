package com.springboot.service;

import com.springboot.bean.Student;

public interface StudentService {
	int add(Student student);
    int update(Student student);
    int deleteById(Integer id);
    Student queryStudentById(Integer id);
}
