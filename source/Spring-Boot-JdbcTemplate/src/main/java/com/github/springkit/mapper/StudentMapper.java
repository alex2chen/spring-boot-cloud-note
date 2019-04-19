package com.github.springkit.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.springkit.po.Student;
import org.springframework.jdbc.core.RowMapper;


public class StudentMapper implements RowMapper<Student>{

	@Override
	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
		Student student = new Student();
		student.setId(rs.getInt("id"));
		student.setName(rs.getString("name"));
		student.setAge(rs.getInt("age"));
		return student;
	}
}
