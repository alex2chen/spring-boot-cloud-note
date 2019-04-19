package com.github.springkit.service.impl;

import com.github.springkit.po.Student;
import com.github.springkit.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.springkit.mapper.StudentMapper;

@Service("studentService")
public class StudentServiceImp implements StudentService {

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
