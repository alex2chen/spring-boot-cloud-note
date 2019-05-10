package com.github.springkit.service;


import com.github.springkit.entity.Student;

public interface StudentService {

    Student getStudent();

    Student getStudentByDs1();

    Student getStudentByDs2();

    Student embedInvoke();

    Student embedInvoke_fixed();

    void addStudent();
    void addStudent_fixed();
    void addStudentManualTransaction();
}