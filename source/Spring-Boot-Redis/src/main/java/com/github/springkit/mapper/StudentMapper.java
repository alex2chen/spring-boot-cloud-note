package com.github.springkit.mapper;

import com.github.springkit.po.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.cache.annotation.CacheConfig;

@Mapper
@CacheConfig(cacheNames = "student")
public interface StudentMapper {

	@Update("update student set name =#{name},age=#{age} where id=#{id}")
	int update(Student student);

	@Delete("delete from student where id=#{id}")
	void deleteStudentByID(Integer id);

	@Select("select * from student where id=#{id}")
	@Results(id = "student", value = { @Result(property = "id", column = "id", javaType = Integer.class),
			@Result(property = "name", column = "name", javaType = String.class),
			@Result(property = "age", column = "age", javaType = Integer.class) })
	Student queryStudentByID(Integer id);
}
