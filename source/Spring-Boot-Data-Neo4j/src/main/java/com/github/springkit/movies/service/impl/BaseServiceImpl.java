package com.github.springkit.movies.service.impl;

import com.github.springkit.movies.domain.BaseEntity;
import com.github.springkit.movies.repository.DbContext;
import com.github.springkit.movies.service.BaseService;

/**
 * BaseServiceImpl
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2016/9/13
 */
public class BaseServiceImpl implements BaseService {
    private DbContext repository;

    public DbContext getRepository() {
        if (repository == null) {
            throw new NullPointerException("repository is null");
        } else {
            return repository;
        }
    }

    @Override
    public <T extends BaseEntity> Iterable<T> findAll() {
        return this.getRepository().findAll();
    }

    @Override
    public <T extends BaseEntity> T selectOne(Long id) {
        return (T) this.getRepository().find(id);
    }

    @Override
    public void delete(Long id) {
        this.getRepository().delete(id);
    }

    @Override
    public <T extends BaseEntity> T createOrUpdate(T entity) {
        return (T) this.getRepository().createOrUpdate(entity);
    }
}
