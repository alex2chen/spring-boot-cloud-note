package com.github.springkit.dao;

import java.util.List;

import com.github.springkit.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPermissionMapper {
	
	List<Permission> findByUserName(String userName);
}
