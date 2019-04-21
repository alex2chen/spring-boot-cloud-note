package com.github.springkit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class UserRepository {

	@Autowired
	private RedisTemplate<String, User> template;	
	
	public void add(User user) {
		template.opsForValue().set(user.getLogin(), user);
	}
	
	public User get(String key) {
		return template.opsForValue().get(key);
	}
	
}
