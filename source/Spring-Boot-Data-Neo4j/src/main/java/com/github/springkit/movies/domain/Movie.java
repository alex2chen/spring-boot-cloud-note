package com.github.springkit.movies.domain;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

/**
 * Movie
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2016/9/17
 */
@NodeEntity(label = "MOVIES")
public class Movie extends BaseEntity {
    private String title;
    private int released;
    private String tagline;
    @Relationship(type = "ACTED_IN", direction = Relationship.INCOMING)
    private List<Role> roles;

    public Movie() {
        roles = new ArrayList<>();
    }

    public Movie(String title, int released, String tagline) {
        this();
        this.title = title;
        this.released = released;
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleased() {
        return released;
    }

    public void setReleased(int released) {
        this.released = released;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + getId() +
                ",title='" + title + '\'' +
                ", released=" + released +
                ", tagline='" + tagline + '\'' +
                ", roles=" + roles.size() +
                '}';
    }
}
