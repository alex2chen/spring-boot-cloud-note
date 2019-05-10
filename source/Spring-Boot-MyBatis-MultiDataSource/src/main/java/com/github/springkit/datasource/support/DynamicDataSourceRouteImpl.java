package com.github.springkit.datasource.support;

import com.github.springkit.config.DataSourceConfiguration;
import com.github.springkit.datasource.DynamicDataSourceRoute;
import com.github.springkit.datasource.DataSourceContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

public class DynamicDataSourceRouteImpl extends AbstractRoutingDataSource implements DynamicDataSourceRoute {
    private static Log log = LogFactory.getLog(DataSourceConfiguration.class);

    @Override
    protected Object determineCurrentLookupKey() {
        Object key = DataSourceContextHolder.getDataSourceType();
        log.info("key:" + key);
        return key;
    }

    @Override
    public DataSource getDefaultDataSource() {
        return super.determineTargetDataSource();
    }

    @Override
    public DataSource getDataSourceByKey(String key) {
        DataSourceContextHolder.setDataSourceType(key);
        return super.determineTargetDataSource();
    }
}
