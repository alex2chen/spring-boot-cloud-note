package com.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.bean.Student;
import com.springboot.mapper.StudentMapper;
import com.springboot.service.StudentService;

@Service("studentService")
public class StudentServiceImp implements StudentService{

	@Autowired
	private StudentMapper studentMapper;
	
	@Override
	public int add(Student student) {
		return this.studentMapper.add(student);
	}

	@Override
	public int update(Student student) {
		return this.studentMapper.update(student);
	}

	@Override
	public int deleteById(Integer id) {
		return this.studentMapper.deleteById(id);
	}

	@Override
	public Student queryStudentById(Integer id) {
		return this.studentMapper.queryStudentById(id);
	}
}
