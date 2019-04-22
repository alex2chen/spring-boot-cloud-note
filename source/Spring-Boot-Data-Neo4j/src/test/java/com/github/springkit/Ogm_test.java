package com.github.springkit;

import com.github.springkit.movies.domain.Person;
import com.google.common.collect.Lists;
import com.github.springkit.movies.domain.Movie;
import com.github.springkit.movies.domain.Role;
import com.github.springkit.movies.repository.Neo4jSessionFactory;
import com.github.springkit.movies.service.MovieService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Ogm_test
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2016/9/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-neo4j.xml")
public class Ogm_test {
    @Autowired
    private MovieService movieService;

    private Session session;

    @Autowired
    private void getNeo4jSession(Neo4jSessionFactory sessionFactory) {
        session = sessionFactory.getNeo4jSession();
    }

    @Test
    public void simple_add_test() {
        Movie movie = new Movie();
        movie.setTitle("大话西游");
        movie.setTagline("最搞笑的爱情片");
        movie.setReleased(2001);
        movieService.createOrUpdate(movie);
        System.out.println(movie);
        Assert.assertNotNull(movie.getId());
    }

    @Test
    public void queryById() {
        Movie movie = movieService.selectOne(781L);
        System.out.println(movie);
        Assert.assertNotNull(movie);
    }

    @Test
    public void update_test() {
        Movie movie = movieService.selectOne(781L);
        movie.setTagline("好2");
        movieService.createOrUpdate(movie);
        System.out.println(movie);
        Assert.assertNotNull(movie.getId());
    }

    @Test
    public void del_test() {
        movieService.delete(781L);
    }

    @Test
    public void addRelection_test() {
        Movie movie = new Movie("大话西游", 2010, "最搞笑的爱情片");
        Person person = new Person("星爷", 1990);
        Role role = new Role();
        role.setMovie(movie);
        role.setPerson(person);
        role.setRoles(Lists.newArrayList("导演", "主演"));
        movieService.createOrUpdate(role);
        System.out.println(movie);
        System.out.println(person);
        System.out.println(role);
        //Assert.assertNotNull(result.getId());
    }

    @Test
    public void queyAll() {
        Iterable<Movie> movies = movieService.findAll();
        System.out.println(movies);
        Assert.assertNotNull(movies);
    }

    @Test
    public void queryByCustom() {
//        String query = "MATCH (m:MOVIES)<-[:ACTED_IN]-(p:Person) return p, count(m) as count ORDER BY count DESC";
//
//        String query2 = "MATCH (s:MOVIES)<-[:ACTED_IN]-(p:Person) return p,s;";
//        Iterable<Map<String, Object>> result = session.query(query, Collections.EMPTY_MAP);
//        while (result.iterator().hasNext()) {
//            Map<String, Object> item = result.iterator().next();
//        }
//        System.out.println(result);
    }

}
