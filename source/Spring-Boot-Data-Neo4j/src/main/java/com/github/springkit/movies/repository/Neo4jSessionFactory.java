package com.github.springkit.movies.repository;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * Neo4jSessionFactory
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2016/9/13
 */
@Repository
public class Neo4jSessionFactory {
    private final static SessionFactory sessionFactory = new SessionFactory("com.xfboy.movies.domain");

    //    private static Neo4jSessionFactory factory = new Neo4jSessionFactory();
//    private Neo4jSessionFactory() {
//    }
//    public static Neo4jSessionFactory getInstance() {
//        return factory;
//    }
    public Session getNeo4jSession() {
        return sessionFactory.openSession();
    }
}
