
[源码](https://github.com/alex2chen/dynamic-datasource.git)
### 使用

```java
    @Override
    @ChooseDataSource(DataSourceKey.DS1_MASTER)
    public Student getStudentByDs1() {
        StudentExample example = new StudentExample();
        return selectOne(studentMapper.selectByExample(example));
    }

    @Override
    @ChooseDataSource(DataSourceKey.DS2_MASTER)
    public Student getStudentByDs2() {
        StudentExample example = new StudentExample();
        return selectOne(studentMapper.selectByExample(example));
    }
```
### 入坑记
- 1.mybatis-spring-boot-starter上折腾了2天，多数据源上没有想象中的好用
- 2.bean重名加载问题：不会再加载(方法student2不会被执行)说明顺序很重要
```java
    @Bean("student")
    public Student student() {
        return new Student();
    }
    @Bean("student")
    public Student student2() {
        return new Student();
    }
```
- 3.@Bean修饰的方法有时会拿不到所属类@Autowired修饰的成员变量：建议只访问是常量
```
    @Configuration
    public class MyConfig{
        @Autowired
        private Environment env;
        @Bean("sqlSessionFactory")
        public SqlSessionFactory sqlSessionFactory() throws Exception {
            log.info("env 有时会是null");
        }
    }
```
- 4.一个方法中存在跨数据源，使用@Transactional时只会访问一个数据源,问题定位：DataSourceTransactionManager.doBegin处。原来在开启事务的时候，Spring会默认马上去取得数据源，并且把它缓存到DataSourceTransactionObject对象中，用于后续的commit, rollback等事务操作，所以我们后续尽管切换AbstractRoutingDataSource, 对事务已然无效。
- 5.@EnableConfigurationProperties({MybatisProperties.class})值一直为空的问题，未定位
- 6.需优化，切换数据注解支持到类级别（如果方法没有使用类级别的）
