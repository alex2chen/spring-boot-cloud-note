package com.github.springkit.datasource;

import javax.sql.DataSource;

/**
 * 计划做到动态加载创建数据源，druid太多配置了，感觉意义不大
 */
@Deprecated
public interface DynamicDataSourceRegister {
    DataSource getDefaultDataSource();

    DataSource[] getCustomDataSources();
}



