package com.github.springkit.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@AutoConfigureAfter(MyBatisConfiguration.class)
//@EnableConfigurationProperties({MybatisProperties.class})
public class MybatisScannerConfiguration {
    private static Log log = LogFactory.getLog(MybatisScannerConfiguration.class);
    private ObjectMapper mapper = new ObjectMapper();
    @Value("${mybatis.typeAliasesPackage}")
    private String typeAliasesPackage;

    @Bean
    @ConditionalOnMissingBean
    public MapperScannerConfigurer mapperScannerConfigurer(Environment env, MybatisProperties prop) throws JsonProcessingException {
        log.info("--------------------init. mapperScannerConfigurer" + mapper.writeValueAsString(prop)+","+typeAliasesPackage);
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        mapperScannerConfigurer.setBasePackage(prop.getTypeAliasesPackage());
        mapperScannerConfigurer.setBasePackage(env.getProperty("mybatis.typeAliasesPackage"));
//        mapperScannerConfigurer.setBasePackage(typeAliasesPackage);
        return mapperScannerConfigurer;
    }
}
