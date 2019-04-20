package com.github.springkit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.github.springkit.pojo.Role;

@Mapper
public interface UserRoleMapper {
	
	List<Role> findByUserName(String userName);
}
