package com.github.springkit.config;

import com.github.springkit.datasource.ChooseDataSource;
import com.github.springkit.datasource.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-1)//保证在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, ChooseDataSource ds) throws Throwable {
        String dsId = ds.value();
        if (!DataSourceContextHolder.containsDataSource(dsId)) {
            logger.info("数据源[{}]不存在，使用默认数据源 > {}", dsId, point.getSignature());
        } else {
            logger.info("Use DataSource : {} > {}", dsId, point.getSignature());
            DataSourceContextHolder.setDataSourceType(dsId);
        }
    }

    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, ChooseDataSource ds) {
        logger.info("Revert DataSource : {} > {}", ds.value(), point.getSignature());
        DataSourceContextHolder.clearDataSourceType();
    }

}
