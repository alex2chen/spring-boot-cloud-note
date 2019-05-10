package com.github.springkit.datasource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/15
 */
public interface DynamicDataSourceRoute extends DataSource{
    void setTargetDataSources(Map<Object, Object> targetDataSources);

    void setDefaultTargetDataSource(Object defaultTargetDataSource);

    DataSource getDefaultDataSource();

    DataSource getDataSourceByKey(String key);
}
