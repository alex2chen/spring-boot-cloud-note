package com.github.springkit.movies.repository.impl;

import com.github.springkit.movies.domain.Movie;
import com.github.springkit.movies.repository.MovieRepository;
import org.springframework.stereotype.Repository;

/**
 * MovieRepositoryImpl
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2016/9/17
 */
@Repository
public class MovieRepositoryImpl extends DbContextImpl<Movie> implements MovieRepository {
    @Override
    public Class<Movie> getEntityType() {
        return Movie.class;
    }
}
