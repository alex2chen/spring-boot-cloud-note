package com.github.springkit.config;

import java.net.UnknownHostException;
import java.util.Date;

import com.github.springkit.domain.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories("com.github.springkit")
public class OrderRepositoryConfig {

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(
                new SimpleMongoDbFactory(  // Spring API
                        new MongoClient(), // driver API
                        "odm_springdata"));
    }

    @Bean
    public OrderBeforeSaveListener beforeSaveListener() {
        return new OrderBeforeSaveListener();
    }

    public class OrderBeforeSaveListener extends AbstractMongoEventListener<Order> {
        @Override
        public void onBeforeSave(BeforeSaveEvent<Order> event) {
            if (event.getSource().getId() == null) {
                // TODO use a better UUID generator in production
                event.getDBObject().put("_id", "" + Math.random());
            }
            if (event.getSource().getDate() == null) {
                event.getDBObject().put("date", new Date());
            }
        }

    }
}