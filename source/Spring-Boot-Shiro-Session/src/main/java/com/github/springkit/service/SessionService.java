package com.github.springkit.service;

import java.util.List;

import com.github.springkit.pojo.UserOnline;

public interface SessionService {
	
	List<UserOnline> list();
	boolean forceLogout(String sessionId);
}
