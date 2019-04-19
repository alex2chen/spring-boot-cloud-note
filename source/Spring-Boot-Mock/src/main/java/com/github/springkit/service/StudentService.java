package com.github.springkit.service;

import com.github.springkit.po.Student;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

public interface StudentService {
    Student update(Student student);
    Student queryStudentById(Integer id);
}
