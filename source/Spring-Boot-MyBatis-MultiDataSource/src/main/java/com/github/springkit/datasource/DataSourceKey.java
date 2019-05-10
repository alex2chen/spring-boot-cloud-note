package com.github.springkit.datasource;

public interface DataSourceKey {
    final static String BATCH_MASTER = "main";
    final static String BATCH_PROPS = "spring.datasource.druid.main";
    final static String DS1_MASTER = "ds1";
    final static String DS1_PROPS = "spring.datasource.druid.ds1";
    final static String DS2_MASTER = "ds2";
    final static String DS2_PROPS = "spring.datasource.druid.ds2";
}
