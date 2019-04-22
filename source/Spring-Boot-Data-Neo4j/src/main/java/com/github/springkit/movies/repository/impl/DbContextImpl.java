package com.github.springkit.movies.repository.impl;

import com.github.springkit.movies.domain.BaseEntity;
import com.github.springkit.movies.repository.DbContext;
import com.github.springkit.movies.repository.Neo4jSessionFactory;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * DaoImpl
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2016/9/13
 */
public abstract class DbContextImpl<T extends BaseEntity> implements DbContext {
    private static final int DEPTH_LIST = 0;
    private static final int DEPTH_ENTITY = 1;
    @Autowired
    private Neo4jSessionFactory sessionFactory;

    @Override
    public Iterable findAll() {
        return sessionFactory.getNeo4jSession().loadAll(getEntityType(), DEPTH_LIST);
    }

    @Override
    public T find(Long id) {
        return sessionFactory.getNeo4jSession().load(getEntityType(), id, DEPTH_ENTITY);
    }

    @Override
    public void delete(Long id) {
        sessionFactory.getNeo4jSession().delete(sessionFactory.getNeo4jSession().load(getEntityType(), id));
    }

    @Override
    public BaseEntity createOrUpdate(BaseEntity entity) {
        Session session=sessionFactory.getNeo4jSession();
//        try (Transaction tx = session.beginTransaction()){
            session.save(entity,DEPTH_ENTITY);
//            tx.commit();
//        }catch (RuntimeException ex){
//            throw ex;
//        }
//        return entity;
        return entity.getId() == null ? entity : find(entity.getId());
    }

    public abstract Class<T> getEntityType();
}
