package com.github.springkit.service;

import com.github.springkit.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceFallback implements UserService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public User get(Long id) {
        return new User(-1L, "default", "123456");
    }

    @Override
    public List<User> get() {
        return null;
    }

    @Override
    public void add(User user) {
        log.info("test fallback");
    }

    @Override
    public void update(User user) {
        log.info("test fallback");
    }

    @Override
    public void delete(Long id) {
        log.info("test fallback");
    }
}
