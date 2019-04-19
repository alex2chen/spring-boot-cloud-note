package com.kxtx.boot.config.partition;

import com.kxtx.boot.model.Employee;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: alex
 * @Description:
 * @Date: created in 2018/12/11.
 */
@Configuration
public class ExportPartitionJobConfiguration {

//    @Bean
//    @StepScope
//    public JpaPagingItemReader<Employee> jpaPagingItemReader(
//            @Value("#{stepExecutionContext['minValue']}") Long minValue,
//            @Value("#{stepExecutionContext['maxValue']}") Long maxValue) {
//        System.err.println("接收到分片参数[" + minValue + "->" + maxValue + "]");
//        JpaPagingItemReader<Employee> reader = new JpaPagingItemReader<>();
//        JpaNativeQueryProvider queryProvider = new JpaNativeQueryProvider<>();
//        String sql = "select * from kl_article where  arcid >= :minValue and arcid <= :maxValue";
//        queryProvider.setSqlQuery(sql);
//        queryProvider.setEntityClass(Employee.class);
//        reader.setQueryProvider(queryProvider);
//        Map queryParames = new HashMap();
//        queryParames.put("minValue", minValue);
//        queryParames.put("maxValue", maxValue);
//        reader.setParameterValues(queryParames);
//        reader.setEntityManagerFactory(new EntityManagerFactory());
//        return reader;
//    }
}
