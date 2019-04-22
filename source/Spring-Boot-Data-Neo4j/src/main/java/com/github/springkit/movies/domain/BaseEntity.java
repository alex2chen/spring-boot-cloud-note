package com.github.springkit.movies.domain;

import org.neo4j.ogm.annotation.GraphId;

/**
 * Entity
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2016/9/13
 */
public abstract class BaseEntity {
    @GraphId
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || id == null || getClass() != o.getClass()) return false;
//        BaseEntity entity = (BaseEntity) o;
//
//        if (!id.equals(entity.id)) return false;
//
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        return (id == null) ? -1 : id.hashCode();
//    }
}
