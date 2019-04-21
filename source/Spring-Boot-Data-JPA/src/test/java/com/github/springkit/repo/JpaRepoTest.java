package com.github.springkit.repo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import com.github.springkit.domain.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JpaRepoTest {

    @Autowired
    UserRepository repo;

    @Autowired
    ClassicUserRepository classicRepo;

    @Autowired
    private ApplicationContext applicationContext;
    @Test
    public void go_showBeans() {
        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(x->System.out.println(applicationContext.getBean(x).getClass()));

    }

    @Before
    public void setUp() {
        for (int i = 0; i < 6; i++) {
            repo.save(new User(String.format("user%02d", i), "User " + i));
        }
    }

    @Test
    public void shouldUseClassicRepository() {
        List<User> users;

        // when
        users = classicRepo.findByFullName("User 1");

        // then
        assertUserByFullName(users, "User 1");
    }

    @Test
    public void shouldPageUsers() {
        List<User> users;

        // when
        Page<User> page = repo.findAll(new PageRequest(2, 2));
        users = page.getContent();

        // then
        assertUserCount(users, 2);
    }

    @Test
    public void shouldFindByFullnameQuery() {
        List<User> users;

        // when
        users = repo.findByFullName("User 5");

        // then
        assertUserByFullName(users, "User 5");
    }

    @Test
    public void shouldFindByFullnameQueryWithSort() {
        List<User> users;

        // when
        users = repo.findByFullName("User 5", new Sort(new Sort.Order(Sort.Direction.DESC, "fullName")));

        // then
        assertUserByFullName(users, "User 5");
    }

    @Test
    public void shouldUseExistingNamedQuery() {
        List<User> users;

        // when
//        users = repo.findByUser5();
//
//        // then
//        assertUserByFullName(users, "User 5");
    }

    @Test
    public void shouldUseXmlNamedQuery() {
        List<User> users;

        // when
        users = repo.findByOrm();

        // then
        assertUserByFullName(users, "User 2");
    }

    @Test
    public void shouldUseSpringDataQuery() {
        List<User> users;

        // when
        users = repo.findByGivenQuery();

        // then
        assertUserByFullName(users, "User 3");
    }

    @Test
    public void shouldIgnoreNullQueryParameters() {
        List<User> usersById, usersByFullName;

        // when
        usersById = repo.findByIdAndFullName("user01", null);
        usersByFullName = repo.findByIdAndFullName(null, "User 01");

        // then
        assertUserCount(usersById, 0);
        assertUserCount(usersByFullName, 0);
    }

    @Test
    public void shouldSortByTwoCriteria() {
        List<User> users;

        // when
        users = repo.findAll(new Sort(
                        new Sort.Order(Sort.Direction.ASC, "id"),
                        new Sort.Order(Sort.Direction.DESC, "fullName")
                )
        );

        // then
        assertUserCount(users, 6);
    }

    private static void assertUserByFullName(List<User> users, String fullName) {
        assertUserCount(users, 1);
        assertThat("Mismatch full name", users.get(0).getFullName(), is(fullName));
    }

    private static void assertUserCount(List<User> users, int expected) {
        assertThat(users, notNullValue());
        assertThat("Mismatch user count", users.size(), is(expected));
    }

}
