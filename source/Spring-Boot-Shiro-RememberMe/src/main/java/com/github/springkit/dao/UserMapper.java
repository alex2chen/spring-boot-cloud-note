package com.github.springkit.dao;

import com.github.springkit.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
	User findByUserName(String userName);
}
