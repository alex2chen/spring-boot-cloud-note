package com.github.springkit.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.github.springkit.pojo.Permission;

@Mapper
public interface UserPermissionMapper {
	
	List<Permission> findByUserName(String userName);
}
