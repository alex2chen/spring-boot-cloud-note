package com.github.springkit.dao;

import java.util.List;

import com.github.springkit.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {
	
	List<Role> findByUserName(String userName);
}
