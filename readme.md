### spring cloud 深入研究领域
- [api gateway Zuul](https://springcloud.cc/spring-cloud-dalston.html#_router_and_filter_zuul)
- [Spring Cloud Contract](https://springcloud.cc/spring-cloud-dalston.html#_spring_cloud_contract)

### SpringBoot学习系列
- [SpringBoot配置属性系列](http://www.cnblogs.com/softidea/p/6068128.html)

#### 1.application.yml和bootstrap.yml区别？
application.yml 是应用级别的配置 bootstrap.yml是系统级别的配，一般不变的放到bootstrap.yml 需要随业务变得放到application.yml
#### 2.springboot新增改进注解
	@Autowired            忽略吧
	@ActiveProfiles       忽略吧
	@Profile              忽略吧
	@Aspect               忽略吧
	@PointCut             忽略吧
	@Around               忽略吧	
	@AointCut             忽略吧
	@Repository           忽略吧
	@Service              忽略吧
	@Component            忽略吧
	@Controller           忽略吧
	@RequestMapping       忽略吧
	@ControllerAdvice     忽略吧
    @EnableWebMvc         忽略吧(开启Web Mvc支持,关于RequestMappingHandlerMapping,RequestMappingHandlerAdapter)
	@ExceptionHandler     忽略吧(全局处理异常,关于ExceptionHandlerExceptionResolver的)
	@InitBinder           忽略吧(设置WebDataBinder,WebDataBinder用来自动绑定前台参数到mdel中)
	@RequestPart          忽略吧(绑定“multipart/form-data”参数)
	@ModelAttribute       忽略吧
	@Cacheable            忽略吧
	@CacheEvict           忽略吧	
	@CachePut             忽略吧
	@PostConstruct        忽略吧
	@PreDestroy           忽略吧
	@Resource             忽略吧
	@RequestMapping       忽略吧
	@RequestParam         忽略吧
	@PathVariable         忽略吧
	@RequestBody          忽略吧
	@ResponseBody         忽略吧
	@RequestHeader        忽略吧
	@Required             忽略吧
	@RestController       忽略吧
	@Lazy                 忽略吧
	@Scope                忽略吧
	@Transcational        忽略吧
	@Value                忽略吧
	@WebAppConfiguration  忽略吧
	@Bean                           实例化bean
	@SpringBootApplication          等价于@Configuration + @ComponentScan + @EnableAutoConfiguration
	@ComponentScan                  扫描包
	@Configuration                  配置类
	@Import                         导入配置类或者Bean
	@ImportResource                 导入XML配置文件
	@AutoConfigurationPackage       将加载所有符合条件的@Configuration配置
	@EnableAutoConfiguration        auto-configuration,@AutoConfigurationPackage + @Import({EnableAutoConfigurationImportSelector.class})
	@PropertySource                 加载属性文件,绑定值,必须使用properties文件
	@ConfigurationProperties        绑定属性值,适用于类或方法(第三方组件),支持javax.validation注解校验
	@EnableConfigurationProperties  开启对@ConfigurationProperties注解配置Bean的支持
	@ConfigurationPropertiesBinding 绑定属性时属性转换	
	
	@ServletComponentScan          注入@WebServlet,@WebFilter,@WebListener的类
	@GetMapping                    Spring4.3中引进的,组合注解,是@RequestMapping(method = RequestMethod.GET)的缩写 
	@PostMapping                   同上
	@PutMapping                    同上
	@DeleteMapping                 同上
	@CrossOrigin                   ajax跨域
	@JsonComponent                 自定义JSON序列化器和反序列化器
	@EnableRedisHttpSession        启用Redis存储管理HttpSession
	@EnableWebSocketMessagetBroker 开启WebSocket支持
	@MessageMapping                websocket
	
	@EnableScheduling               开启对计划任务的支持
	@Scheduled                      声明这是一个计划任务，方法返回类型为void，支持cron、fixDelay、fixRate
	@EnableAsync                    开启对异步任务的支持
	@Async                          异步任务方法或者异步类
	@Qualifier                      可以单独在方法中使用,改进新特性吧，相当于@Autowired
	@Primary                        唯一可用
	@EnableAspectJAutoProxy         启用AOP,AnnotationAwareAspectJAutoProxyCreator
		
	@EnableCaching                  开启缓存支持
	@CacheConfig                    类中指定cacheNames的,对@CacheEvict(value = "user")重复定义的改进
	
	@EnableSpringSecurity           开启spring seccurity支持
	@EnableGlobalMethodSecurity     启用'basic'认证
	@EnableAuthorizationServer      oauth2 access tokens
	@EnableOAuth2Client             安全
	@EnableOAuth2Sso                安全
	
	@EnableTransactionManagement    开启注解式事务的支持
	@EnableJpaRepositories          开启JPA
	@NoRepositoryBean               JPA
	@EnableJpaRepositories          JPA
	@EntityScan                     JPA
	@Entity                         JPA
	@Embeddable                     JPA
	@MappedSuperclass               JPA
	@NodeEntity                     Neo4j
	@EnableNeo4jRepositories        Neo4j
	@SolrDocument                   Solr
	@Document                       Elasticsearch
	
    @AutoConfigureBefore            auto-configuration,在指定的配置类初始化前加载
	@AutoConfigureAfter             auto-configuration,在指定的配置类初始化后加载
	@AutoConfigureOrder             auto-configuration,数越小越先初始化
	@Conditional                    标注在类上,所有成员都会启用配置
	@ConditionalOnClass             classpath中存在该类时起效
	@ConditionalOnMissingClass      classpath中不存在该类时起效
	@ConditionalOnBean              Bean条件(DI容器中存在)
	@ConditionalOnMissingBean       Bean条件(DI容器中不存在)
	@ConditionalOnSingleCandidate   Bean条件(DI容器中该类型Bean只有一个或@Primary的只有一个时起效)
	@ConditionalOnProperty          参数设置或者值一致时起效
	@ConditionalOnResource          指定的文件存在时起效
	@ConditionalOnExpression        SpEL表达式结果为true时
	@ConditionalOnWebApplication    Web应用环境下起效
	@ConditionalOnNotWebApplication 非Web应用环境下起效
	@ConditionalOnJndi              指定的JNDI存在时起效 
    @ConditionalOnJava              指定的Java版本存在时起效	 	
	
	@EnableIntegration              启用spring-boot-starter-integration基于消息和其他传输协议的抽象,比如HTTP，TCP等
	@EnableJms                      开启JMS支持
	@JmsListener                    创建一个监听者端点,默认是支持事务性的
	@EnableRabbit                   开启Rabbit支持(AMQP)
	@RabbitListener                 创建一个监听者端点
	@RabbitHandler                  监听者处理程序
	@ManagementContextConfiguration Actuator模块
	@ExportMetricWriter             Actuator模块
	@ExportMetricReader             Actuator模块
	@ManagedResource                JMX
	@ManagedAttribute               JMX
	@ManagedOperation               JMX
	
	@SpringBootTest                 Test,添加@RunWith(SpringRunner.class)	
	@TestConfiguration              类似@Configuration
	@TestComponent                  Test
	@LocalServerPort                用于注入测试用例实际使用的端口
	@MockBean                       Test
	@SpyBean                        Test
	@JsonTest                       测试对象JSON序列化和反序列化是否工作正常
	@AutoConfigureJsonTesters        Test
	@WebMvcTest                     检测单个Controller是否工作正常
	@AutoConfigureMockMvc           注解一个non-@WebMvcTest的类
	@DataJpaTest                    JPA测试
	@AutoConfigureTestEntityManager Test
	@AutoConfigureTestDatabase      真实DB/嵌入DB
	@RestClientTest                 Test
	@AutoConfigureRestDocs          Test
	@Rule                           使用OutputCapture,捕获System.out和System.err输出
	@TestRestTemplate               Test

#### 3.@ConfigurationProperties vs @Value？
	特性           ConfigurationProperties @Value	
	Relaxed绑定    Yes                     No
	Meta-data支持  Yes                     No
	SpEL表达式     No                      Yes
	Relaxed绑定：松绑定,Environment属性名和bean属性名不需要精确匹配，如firstName可以表示为：
	属性                 说明
	person.firstName     标准驼峰规则
	person.first-name    虚线表示，推荐用于.properties和.yml文件中
	person.first_name    下划线表示，用于.properties和.yml文件的可选格式
	PERSON_FIRST_NAME    大写形式，使用系统环境变量时推荐
    Meta-data支持：支持spring元数据,满足spring原数据格式http://docs.spring.io/spring-boot/docs/1.4.1.RELEASE/reference/htmlsingle/#configuration-metadata	
#### 4.@Configuration vs Auto-configuration？
	@Configuration替代xml来定义BeanDefinition的一种手段。Auto-configuration也是定义BeanDefinition的一种手段。<br/>
	这两者的相同之处有：<br/>
	   都是使用@Configuration注解的类，这些类里都可以定义@Bean、@Import、@ImportResource。<br/>
	   都可以使用@Condition*来根据情况选择是否加载<br/>
	而不同之处有：<br/>
       加载方式不同：<br/>
          普通@Configuration则是通过扫描package path加载的<br/>
          Auto-configuration的是通过读取classpath*:META-INF/spring.factories中key等于org.springframework.boot.autoconfigure.EnableAutoConfiguration的property列出的@Configuration加载的<br/>
       加载顺序不同：普通@Configuration的加载永远在Auto-configuration之前<br/>
       内部加载顺序可控上的不同：<br/>
         普通@Configuration则无法控制加载顺序<br/>
         Auto-configuration可以使用@AutoConfigureOrder、@AutoConfigureBefore、@AutoConfigureAfter
#### 5.常用对象
	SpringApplication
	AnnotationConfigApplicationContext
	AnnotationConfigEmbeddedWebApplicationContext 
	AnnotatedBeanDefinitionReader
	ClassPathBeanDefinitionScanner
	
	ApplicationStartedEvent                       启动监听>spring boot在启动过程
	ApplicationEnvironmentPreparedEvent           监听配置环境事件但此时上下文context还没有创建>....				
	ApplicationPreparedEvent                      监听上下文创建完成后      
	ApplicationFailedEvent                        监听启动异常时
	
	GenericTypeResolver
	AnnotationConfigUtils
	PropertiesLoaderUtils
	.....
#### 6.原理及源码分析系列
- [事件监听源码分析](http://blog.csdn.net/liaokailin/article/details/48194777)<br/>
	EventPublishingRunListener	<br/>
	SimpleApplicationEventMulticaster	<br/>
- [配置源码解析](http://blog.csdn.net/liaokailin/article/details/48864737)<br/>
	StandardServletEnvironment	<br/>
	AbstractEnvironment	<br/>
	MutablePropertySources	<br/>
	MapPropertySource(动态配置)	<br/>
- [加载application资源文件源码分析](http://blog.csdn.net/liaokailin/article/details/48878447)<br/>
	ConfigFileApplicationListener	<br/>
	PropertySourceLoader	<br/>
	RandomValuePropertySource	<br/>
- [Spring boot Bean加载源码分析](http://blog.csdn.net/liaokailin/article/details/49107209)<br/>
	ConfigurationWarningsApplicationContextInitializer	<br/>
	ConfigurationWarningsPostProcessor	<br/>
	PropertySourceOrderingPostProcessor	<br/>
- [自动配置原理分析](http://blog.csdn.net/liaokailin/article/details/49559951)<br/>
	EnableAutoConfigurationImportSelector	<br/>
	AutoConfigurationMetadataLoader		<br/>
- [嵌入tomcat源码分析](http://blog.csdn.net/liaokailin/article/details/52269786)<br/>
#### 7.常见jar迁移  
	druid -> 			druid-spring-boot-starter
	mybatis-spring -> mybatis-spring-boot-starter:MybatisAutoConfiguration(自动注入@Mapper)
	jedis ->			pring-boot-starter-data-redis
	com.github.pagehelper:pagehelper
	.....
### SpringCloud学习系列  
#### 1.springCloud常见注解
	@EnableEurekaServer      eureka-server
	@EnableDiscoveryClient   eureka-client
	
	@EnableCircuitBreaker    Ribbon
    @LoadBalanced            Ribbon
	
	@EnableFeignClients      feign
	@FeignClient             feign
	
	@EnableConfigServer      spring-config
	@RefreshScope            spring-config
	
	@EnableCircuitBreaker    hystrix
	@EnableHystrix           hystrix(上一个简写)
	@HystrixCommand          hystrix
	@EnableTurbine           turbine
	@EnableTurbineStream     turbine-amqp
	@EnableHystrixDashboard  hystrix-dashboard
	
	@EnableZuulProxy         zuul
	
	@InboundChannelAdapter   stream
	@StreamListener          stream
	@Poller                  stream
	@Transformer             stream
	@Output                  stream
	@EnableBinding           stream
	
	@EnableZipkinServer        sleuth-zipkin
	@EnableZipkinStreamServer  sleuth-zipkin-stream
#### 3.常用对象
	RestTemplate
#### 4.为什么这个请求有效？http://COMPUTE-SERVICE/add?a=10&b=20   
	其实是ribbon的@LoadBalanced启作用的