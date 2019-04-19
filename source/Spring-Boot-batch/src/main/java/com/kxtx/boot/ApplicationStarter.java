package com.kxtx.boot;

import static springfox.documentation.builders.PathSelectors.regex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Configuration
@EnableSwagger2
@ComponentScan("com.kxtx.boot")
//@EnableSpringDataWebSupport
@MapperScan("com.kxtx.boot.repository")
public class ApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("locale/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(categoryPaths())
                .build();
    }

    private Predicate<String> categoryPaths() {
        return regex("/api.*");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot CRUD API Using Undertow Server")
                .description("This is a sample Java / Maven / Spring Boot application that can be used as a starter for creating " +
                        "a micro service complete with built-in Basic CRUD operation and much more."
                        + "Technology Stacks used to create API are as SpringBoot.RELEASE, Spring JPA,UnderTow Server, "
                        + "Mysql ,Swagger for Documentaions .")
                .contact("rohit_pachouli@optum.com")
                .build();
    }
}
