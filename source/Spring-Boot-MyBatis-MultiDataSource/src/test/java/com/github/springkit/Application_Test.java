package com.github.springkit;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.springkit.datasource.support.DynamicDataSourceRouteImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.closeTo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Application_Test {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DynamicDataSourceRouteImpl dynamicDataSource;

    @Test
    public void go_dynmicDataSource() {
        if (dynamicDataSource.getDefaultDataSource() instanceof DruidDataSource) {
            DruidDataSource defaultDataSource = (DruidDataSource) dynamicDataSource.getDefaultDataSource();
            System.out.println(defaultDataSource.getUrl());
            assertThat(defaultDataSource.getInitialSize()).isEqualTo(50);
        }
    }

}
