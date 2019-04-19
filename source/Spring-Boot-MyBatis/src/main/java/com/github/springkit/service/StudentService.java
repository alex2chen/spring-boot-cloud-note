package com.github.springkit.service;

import com.github.springkit.po.Student;

public interface StudentService {
	int add(Student student);
    int update(Student student);
    int deleteById(Integer id);
    Student queryStudentById(Integer id);
}
