package com.github.springkit.dao;

import org.apache.ibatis.annotations.Mapper;

import com.github.springkit.pojo.User;

@Mapper
public interface UserMapper {
	User findByUserName(String userName);
}
