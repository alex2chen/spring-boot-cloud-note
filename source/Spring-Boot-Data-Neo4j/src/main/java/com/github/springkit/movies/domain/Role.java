package com.github.springkit.movies.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.util.Collection;

/**
 * Role
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2016/9/17
 */
@RelationshipEntity(type = "ACTED_IN")
public class Role extends BaseEntity {
    private Collection<String> roles;
    @StartNode
    private Person person;
    @EndNode
    private Movie movie;

    public Role() {
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + getId() +
                ",roles=" + roles.size() +
                ", person=" + person +
                ", movie=" + movie +
                '}';
    }
}
