package com.github.springkit.controller;

import com.github.springkit.po.Student;
import com.github.springkit.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("student/{userName}")
    public Student queryStudentByID(@PathVariable(value = "id") Integer id) {
        return this.studentService.queryStudentById(id);
    }

    @PostMapping("student/save")
    public void update(@RequestBody Student student) {
        this.studentService.update(student);
    }
}
