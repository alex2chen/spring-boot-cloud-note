package com.github.springkit.config;

import com.github.springkit.datasource.DataSourceKey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {
    private static Log log = LogFactory.getLog(DataSourceConfiguration.class);
    @Value("${spring.datasource.type}")
    private Class<? extends DataSource> dataSourceType;
//    @Primary
    @Bean(name = DataSourceKey.BATCH_MASTER)
    @ConfigurationProperties(prefix = DataSourceKey.BATCH_PROPS)
    public DataSource writeDataSource() {
        log.info("--------------------init. main");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = DataSourceKey.DS1_MASTER)
    @ConfigurationProperties(DataSourceKey.DS1_PROPS)
    public DataSource dataSourceOne() {
        log.info("--------------------init. ds1");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = DataSourceKey.DS2_MASTER)
    @ConfigurationProperties(DataSourceKey.DS2_PROPS)
    public DataSource dataSourceTwo() {
        log.info("--------------------init. ds2");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }
}
