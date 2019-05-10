package com.github.springkit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
public class TransactionManagerConfiguration extends DataSourceTransactionManagerAutoConfiguration {
    private final static Logger LOG = LoggerFactory.getLogger(TransactionManagerConfiguration.class);
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManagers(ApplicationContext context) {
        LOG.info("--------------------init.transactionManager");
        return new DataSourceTransactionManager((DataSource) context.getBean("dynamicDataSource"));
    }
}
