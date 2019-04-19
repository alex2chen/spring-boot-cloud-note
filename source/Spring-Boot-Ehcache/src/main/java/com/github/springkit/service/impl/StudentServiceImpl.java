package com.github.springkit.service.impl;

import com.github.springkit.po.Student;
import com.github.springkit.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.springkit.service.StudentService;

@Repository("studentService")
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentMapper studentMapper;
	
	@Override
	public Student update(Student student) {
		this.studentMapper.update(student);
		return this.studentMapper.queryStudentByID(student.getId());
	}

	@Override
	public void deleteStudentByID(Integer id) {
		this.studentMapper.deleteStudentByID(id);
	}

	@Override
	public Student queryStudentByID(Integer id) {
		return this.studentMapper.queryStudentByID(id);
	}

}
