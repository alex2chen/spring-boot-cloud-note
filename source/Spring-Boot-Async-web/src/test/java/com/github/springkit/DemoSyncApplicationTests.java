package com.github.springkit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoSyncApplicationTests {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);
        RequestMappingHandlerAdapter bean = applicationContext.getBean(RequestMappingHandlerAdapter.class);
    }

}
