package com.github.springkit.movies.repository;

import com.github.springkit.movies.domain.BaseEntity;

/**
 * IDao
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2016/9/13
 */
public interface DbContext<T extends BaseEntity> {
    Iterable<T> findAll();

    T find(Long id);

    void delete(Long id);

    T createOrUpdate(T entity);
}
