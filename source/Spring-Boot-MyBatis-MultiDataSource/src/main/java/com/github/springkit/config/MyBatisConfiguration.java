
package com.github.springkit.config;

import com.github.springkit.datasource.DataSourceContextHolder;
import com.github.springkit.datasource.DataSourceKey;
import com.github.springkit.datasource.DynamicDataSourceRoute;
import com.github.springkit.datasource.support.DynamicDataSourceRouteImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@EnableConfigurationProperties({MybatisProperties.class})
@AutoConfigureAfter({DataSourceConfiguration.class})
public class MyBatisConfiguration implements DataSourceKey {
    private static Log log = LogFactory.getLog(MyBatisConfiguration.class);
    private ObjectMapper mapper = new ObjectMapper();
    @Value("${mybatis.mapperLocations}")
    private String mapperLocations;
//    @Autowired
//    private ResourceLoader resourceLoader = new DefaultResourceLoader();
//    @Autowired
//    private Environment env;

    @Bean
    public DynamicDataSourceRoute dynamicDataSource(ApplicationContext context) {
        log.info("--------------------init.dynamicDataSource");
        DataSourceContextHolder.dataSourceIds.add(BATCH_MASTER);
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(BATCH_MASTER, context.getBean(BATCH_MASTER));
        targetDataSources.put(DS1_MASTER, context.getBean(DS1_MASTER));
        targetDataSources.put(DS2_MASTER, context.getBean(DS2_MASTER));
        DataSourceContextHolder.dataSourceIds.add(DS1_MASTER);
        DataSourceContextHolder.dataSourceIds.add(DS2_MASTER);
        DynamicDataSourceRouteImpl dynamicDataSource = new DynamicDataSourceRouteImpl();
        dynamicDataSource.setDefaultTargetDataSource(context.getBean(BATCH_MASTER));
        dynamicDataSource.setTargetDataSources(targetDataSources);
        return dynamicDataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DynamicDataSourceRoute ds, MybatisProperties prop) throws Exception {
        log.info("--------------------init. sqlSessionFactory" + mapper.writeValueAsString(prop));
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);
//        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(Arrays.toString(prop.getMapperLocations())));
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        return fb.getObject();
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        log.info("--------------------init. sqlSessionTemplate");
//        return new SqlSessionTemplate(sqlSessionFactory, this.properties.getExecutorType());
        return new SqlSessionTemplate(sqlSessionFactory);
    }
//    @Autowired(required = false)
//    private Interceptor[] interceptors;
//	/**
//	 * 分页插件
//	 */
//	@Bean
//	public PageHelper pageHelper(DataSource dataSource) {
//		log.info("注册MyBatis分页插件PageHelper");
//		PageHelper pageHelper = new PageHelper();
//		Properties p = new Properties();
//		p.setProperty("offsetAsPageNum", "true");
//		p.setProperty("rowBoundsWithCount", "true");
//		p.setProperty("reasonable", "true");
//		pageHelper.setProperties(p);
//		return pageHelper;
//	}
}
