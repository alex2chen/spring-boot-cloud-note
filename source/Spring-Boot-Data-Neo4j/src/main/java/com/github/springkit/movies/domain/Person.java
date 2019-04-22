package com.github.springkit.movies.domain;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

/**
 * Person
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2016/9/17
 */
@NodeEntity
public class Person extends BaseEntity {
    private String name;
    private int born;
    @Relationship(type = "ACTED_IN")
    private List<Movie> movies;

    public Person() {
        movies = new ArrayList<>();
    }

    public Person(String name, int born) {
        this();
        this.name = name;
        this.born = born;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBorn() {
        return born;
    }

    public void setBorn(int born) {
        this.born = born;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + getId() +
                ",name='" + name + '\'' +
                ", born=" + born +
                ", movies=" + movies.size() +
                '}';
    }
}
