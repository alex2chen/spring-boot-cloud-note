Spring Boot是由Pivotal团队提供的全新框架，其设计目的是用来简化新Spring应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。它已经成为地球上最热门的技术之一。<p/>

直接看[spring cloud笔记](https://github.com/alex2chen/spring-boot-cloud-note/blob/master/spring%20cloud%20note.md)<br/>

### 推荐图书
- Java EE开发的颠覆者 Spring Boot实战
- Spring Boot参考指南

#### 1.application.yml和bootstrap.yml区别？
bootstrap.yml可以理解成系统级别的一些参数配置，这些参数一般是不会变动的<br/>
application.yml 可以用来定义应用级别的，如果搭配spring-cloud-config使用 application.yml里面定义的文件可以实现动态替换
#### 2.springboot新增改进注解
<table><tbody><tr><td>
	<strong>注解</strong>
	</td>
	<td>
<strong>版本</strong>
	</td>
	<td>
	<strong>描述</strong>
	</td>
</tr><tr><td>@Resource
	</td>
	<td>
	-
	</td>
	<td>
	规范
	</td>
</tr><tr><td>
	@PostConstruct
	</td>
	<td>
	-
	</td>
	<td>
	JavaEE5规范
	</td>
</tr><tr><td>
	@PreDestroy
	</td>
	<td>
	<s>-</s>
	</td>
	<td>
	JavaEE5规范
	</td>
</tr><tr><td>
	@Autowired
	</td>
	<td>
	2.5
	</td>
	<td>
	略
	</td>
</tr><tr><td>
	@Qualifier
	</td>
	<td>
	2.5
	</td>
	<td>
	新改进特性：可以单独在方法中使用
	</td>
</tr><tr><td>
	@Required
	</td>
	<td>
	2.0
	</td>
	<td>
	略
	</td>
</tr><tr><td>
	@Scope
	</td>
	<td>
	2.5
	</td>
	<td>
	略
	</td>
</tr><tr><td>
	@Value
	</td>
	<td>
	3.0
	</td>
	<td>
	略
	</td>
</tr><tr><td>
	@PropertySource
	</td>
	<td>
	3.1
	</td>
	<td>
	加载属性文件,绑定值,必须使用properties文件
	</td>
</tr><tr><td>
	@PropertySources
	</td>
	<td>
	4.0
	</td>
	<td>
	PropertySource的集合形式
	</td>
</tr><tr><td>@Repository	
	</td>
	<td>2.0	
	</td>
	<td>略	
	</td>
</tr><tr><td>
	@Service	
	</td>
	<td>
	2.5
	</td>
	<td>略	
	</td>
</tr><tr><td>@Component	
	</td>
	<td>
	2.5
	</td>
	<td>
	略
	</td>
</tr><tr><td>
	@Bean
	</td>
	<td>
	3.0
	</td>
	<td>
	生产bean</td>
</tr><tr><td>
	@DependsOn
	</td>
	<td>
	3.0
	</td>
	<td>
	控制依赖bean加载顺序
	</td>
</tr><tr><td>
	@Lazy
	</td>
	<td>
	3.0
	</td>
	<td>
	Bean懒加载
	</td>
</tr><tr><td>
	@Import
	</td>
	<td>
	3.0
	</td>
	<td>
	可导入Configuration、component classes、
	ImportSelector、ImportResource
	</td>
</tr><tr><td>
	@ImportResource
	</td>
	<td>
	3.0
	</td>
	<td>
	导入XML配置文件
	</td>
</tr><tr><td>
	@Primary
	</td>
	<td>
	3.0
	</td>
	<td>
	在自动装配时这个bean应该优先当多个候选人资格（相同类型的bean）
	</td>
</tr><tr><td>
	@ComponentScan
	</td>
	<td>
	3.1
	</td>
	<td>
	扫描包，可定义@Filter
	</td>
</tr><tr><td>
	@Filter
	</td>
	<td>
	3.1
	</td>
	<td>
	扫描包过滤配置
	</td>
</tr><tr><td>
	@Configuration
	</td>
	<td>
	3.0
	</td>
	<td>
	表明这个是可以声明一个或多个@Bean的配置类
	</td>
</tr><tr><td>@ActiveProfiles	
	</td>
	<td>3.1	
	</td>
	<td>>单元测试中指定环境变量	
	</td>
</tr><tr><td>@Profile	
	</td>
	<td>3.1	
	</td>
	<td>多环节	
	</td>
</tr><tr><td>
	@SpringBootApplication
	</td>
	<td>
	1.2.0
	</td>
	<td>
	表明这是一个spring-boot启动类，等价于@Configuration,@ComponentScan,@EnableAutoConfiguration
	</td>
</tr><tr><td>@SpringBootConfiguration
	</td>
	<td>1.4.0	
	</td>
	<td>表明这个类提供了spring-boot启动应用程序	
	</td>
</tr><tr><td>@EnableAutoConfiguration	
	</td>
	<td>1.3.0	
	</td>
	<td>
	启用自动配置Spring应用程序上下文，它自动根据您的类路径来获取spi（spring.factories）中这些的bean的定义。
	</td>
</tr><tr><td>
	@ConfigurationProperties
	</td>
	<td>
	1.3.0
	</td>
	<td>
	绑定属性值,适用于类或方法(第三方组件),支持javax.validation注解校验
	</td>
</tr><tr><td>
	@EnableConfigurationProperties
	</td>
	<td>
	1.3.0
	</td>
	<td>
	开启对@ConfigurationProperties注解配置Bean的支持
	</td>
</tr><tr><td>
	@ConfigurationPropertiesBinding
	</td>
	<td>
	1.3.0
	</td>
	<td>
	ConfigurationProperties绑定属性时属性转换
	</td>
</tr><tr><td>
	@AutoConfigureAfter
	</td>
	<td>
	1.3.0
	</td>
	<td>
	auto-configuration
	</td>
</tr><tr><td>
	@AutoConfigureBefore
	</td>
	<td>
	1.3.0
	</td>
	<td>
	auto-configuration
	</td>
</tr><tr><td>
	@AutoconfigureOrder
	</td>
	<td>
	1.3.0
	</td>
	<td>
	auto-configuration
	</td>
</tr><tr><td>
	@Conditional
	</td>
	<td>
	4.0
	</td>
	<td>
	该类下面的所有@Bean都会启用配置
	</td>
</tr><tr><td>
	@ConditionalOnClass
	</td>
	<td>
	1.3.0
	</td>
	<td>
	某个class位于类路径上，才会实例化一个Bean
	</td>
</tr><tr><td>
	@ConditionalOnMissingClass
	</td>
	<td>
	1.3.0
	</td>
	<td>
	某个class类路径上不存在的时候，才会实例化一个Bean
	</td>
</tr><tr><td>
	@ConditionalOnBean
	</td>
	<td>
	1.3.0
	</td>
	<td>
	Bean条件
	</td>
</tr><tr><td>
	@ConditionalOnMissingBean
	</td>
	<td>
	1.3.0
	</td>
	<td>
	反之
	</td>
</tr><tr><td>
	@ConditionalOnProperty  
	</td>
	<td>
	1.1.0
	</td>
	<td>
	Property条件
	</td>
</tr><tr><td>
	@ConditionalOnResource
	</td>
	<td>
	1.3.0
	</td>
	<td>
	Resource条件
	</td>
</tr><tr><td>
	@ConditionalOnWebApplication 
	</td>
	<td>
	1.3.0
	</td>
	<td>
	WebApplication条件
	</td>
</tr><tr><td>
	@ConditionalOnNotWebApplication
	</td>
	<td>
	1.3.0
	</td>
	<td>
	反之
	</td>
</tr><tr><td>
	@ConditionalOnExpression 
	</td>
	<td>
	1.3.0
	</td>
	<td>
	SpEL表达式条件
	</td>
</tr><tr><td>
	@ConditionalOnJndi
	</td>
	<td>
	1.2.0
	</td>
	<td>
	基于JNDI的可用性相匹
	</td>
</tr><tr><td>
	@ConditionalOnJava
	</td>
	<td>
	1.1.0
	</td>
	<td>
	指定JVM版本
	</td>
</tr><tr><td>
	@ConditionalOnCloudPlatform
	</td>
	<td>
	1.5.0
	</td>
	<td>
	指定的云平台
	</td>
</tr><tr><td>
	@EnableAsync
	</td>
	<td>
	3.1
	</td>
	<td>
	异步任务的启用
	</td>
</tr><tr><td>
	@Async
	</td>
	<td>
	3.0
	</td>
	<td>
	异步任务方法或者异步类
	</td>
</tr><tr><td>
	@EnableScheduling
	</td>
	<td>
	3.1
	</td>
	<td>
	计划任务的启用
	</td>
</tr><tr><td>
	@Scheduled
	</td>
	<td>
	3.0
	</td>
	<td>
	声明这是一个计划任务，方法返回类型为void，支持cron、fixDelay、fixRate
	</td>
</tr><tr><td>
	@EnableWebSocketMessagetBroker
	</td>
	<td>
	4.0
	</td>
	<td>
	webSocket启用
	</td>
</tr><tr><td>
	@MessageMapping
	</td>
	<td>
	4.0
	</td>
	<td>
	websocket
	</td>
</tr><tr><td>
	@EnableAspectJAutoProxy
	</td>
	<td>
	3.1
	</td>
	<td>
	AOP启用
	</td>
</tr><tr><td>
	@Aspect
	</td>
	<td>
	-
	</td>
	<td>
	aspectj
	</td>
</tr><tr><td>
	@PointCut
	</td>
	<td>
	-
	</td>
	<td>
	aspectj
	</td>
</tr><tr><td>
	@Before
	</td>
	<td>
	-
	</td>
	<td>
	aspectj
	</td>
</tr><tr><td>
	@After
	</td>
	<td>
	-
	</td>
	<td>
	aspectj
	</td>
</tr><tr><td>
	@Around
	</td>
	<td>
	-
	</td>
	<td>
	aspectj
	</td>
</tr><tr><td>
	@AfterReturning
	</td>
	<td>
	-
	</td>
	<td>
	aspectj
	</td>
</tr><tr><td>
	@AfterThrowing
	</td>
	<td>
	-
	</td>
	<td>
	aspectj
	</td>
</tr><tr><td>
	@EnableCaching   
	</td>
	<td>
	3.1
	</td>
	<td>
	缓存启用
	</td>
</tr><tr><td>
	@Cacheable
	</td>
	<td>
	3.1
	</td>
	<td>
	对其结果进行缓存
	</td>
</tr><tr><td>
	@Caching
	</td>
	<td>
	3.1
	</td>
	<td>
	组合多个Cache注解使用
	</td>
</tr><tr><td>
	@CacheEvict  
	</td>
	<td>
	3.1
	</td>
	<td>
	根据一定的条件对缓存进行清空
	</td>
</tr><tr><td>
	@CachePut   
	</td>
	<td>
	3.1
	</td>
	<td>
	和 @Cacheable 不同的是，它每次执行前不会去检查缓存,都会触发真实方法的调用
	</td>
</tr><tr><td>
	@CacheConfig
	</td>
	<td>
	4.1
	</td>
	<td>
	类级别的注解,对词重复（方法多次指定cacheNames的）定义的改进
	</td>
</tr><tr><td>
	@Controller 
	</td>
	<td>
	2.5
	</td>
	<td>
	略
	</td>
</tr><tr><td>
	@RequestMapping
	</td>
	<td>
	2.5
	</td>
	<td>
	略
	</td>
</tr><tr><td>
	@ControllerAdvice
	</td>
	<td>
	3.2
	</td>
	<td>
	方法将应用于所有控制器，可以结合@ExceptionHandler、@InitBinder、@ModelAttribute使用
	</td>
</tr><tr><td>
	@RestControllerAdvice
	</td>
	<td>
	4.3
	</td>
	<td>
	为ControllerAdvice和ResponseBody的组合体
	</td>
</tr><tr><td>
	@CookieValue
	</td>
	<td>
	3.0
	</td>
	<td>
	略
	</td>
</tr><tr><td>
	@SessionAttributes
	</td>
	<td>
	2.5
	</td>
	<td>
	略
	</td>
</tr><tr><td>
	@SessionAttribute
	</td>
	<td>
	4.3
	</td>
	<td>
	将方法参数绑定到一个会话属性
	</td>
</tr><tr><td>
	@CrossOrigin      
	</td>
	<td>
	4.2
	</td>
	<td>
	ajax跨域
	</td>
</tr><tr><td>
	@ExceptionHandler
	</td>
	<td>
	3.0
	</td>
	<td>
	处理异常的具体处理程序类和/或处理程序方法
	</td>
</tr><tr><td>
	@ResponseStatus
	</td>
	<td>
	3.1
	</td>
	<td>
	标志着一个方法或异常类状态应该返回
	</td>
</tr><tr><td>
	@MatrixVariable
	</td>
	<td>
	3.2
	</td>
	<td>
	矩阵变量绑定参数(Map型参数)如：/path;name=value;name2=value2
	</td>
</tr><tr><td>
	@InitBinder
	</td>
	<td>
	2.5
	</td>
	<td>
	WebDataBinder用来自动绑定前台参数到mdel中
	</td>
</tr><tr><td>
	@ModelAttribute
	</td>
	<td>
	2.5
	</td>
	<td>
	略
	</td>
</tr><tr><td>
	@RequestMapping 
	</td>
	<td>
	2.5
	</td>
	<td>
	略
	</td>
</tr><tr><td>
	@RequestAttribute
	</td>
	<td>
	4.3
	</td>
	<td>
	将方法参数绑定到请求属性
	</td>
</tr><tr><td>
	@RequestParam  
	</td>
	<td>
	2.5
	</td>
	<td>
	略
	</td>
</tr><tr><td>
	@PathVariable  
	</td>
	<td>
	3.0
	</td>
	<td>
	略
	</td>
</tr><tr><td>
	@RequestBody  
	</td>
	<td>
	3.0
	</td>
	<td>
	略
	</td>
</tr><tr><td>
	@ResponseBody  
	</td>
	<td>
	3.0
	</td>
	<td>
	略
	</td>
</tr><tr><td>
	@RequestHeader 
	</td>
	<td>
	3.0
	</td>
	<td>
	略
	</td>
</tr><tr><td>
	@RequestPart
	</td>
	<td>
	3.1
	</td>
	<td>
	绑定“multipart/form-data”参数
	</td>
</tr><tr><td>
	@WebAppConfiguration
	</td>
	<td>
	3.2
	</td>
	<td>
	@WebAppConfiguration
	</td>
</tr><tr><td>
	@EnableWebMvc    
	</td>
	<td>
	3.1
	</td>
	<td>
	开启Web Mvc支持
	</td>
</tr><tr><td>
	@RestController
	</td>
	<td>
	4.0
	</td>
	<td>
	为@Controller和@ResponseBody组合体
	</td>
</tr><tr><td>
	@ServletComponentScan
	</td>
	<td>
	1.3.0
	</td>
	<td>
	注入@WebServlet,@WebFilter,@WebListener的类
	</td>
</tr><tr><td>
	@GetMapping
	</td>
	<td>
	4.3
	</td>
	<td>
	组合注解,是@RequestMapping(method = RequestMethod.GET)的缩写
	</td>
</tr><tr><td>
	@PostMapping
	</td>
	<td>
	4.3
	</td>
	<td>
	同上
	</td>
</tr><tr><td>
	@PutMapping 
	</td>
	<td>
	4.3
	</td>
	<td>
	同上
	</td>
</tr><tr><td>
	@DeleteMapping
	</td>
	<td>
	4.3
	</td>
	<td>
	同上
	</td>
</tr><tr><td>
	@PatchMapping
	</td>
	<td>
	4.3
	</td>
	<td>
	同上
	</td>
</tr><tr><td>
	@JsonComponent
	</td>
	<td>
	1.4.0
	</td>
	<td>
	自定义JSON序列化器和反序列化器
	</td>
</tr><tr><td>
	@EnableTransactionManagement
	</td>
	<td>
	3.1
	</td>
	<td>
	事务启用
	</td>
</tr><tr><td>
	@Transactional
	</td>
	<td>
	1.2
	</td>
	<td>
	略
	</td>
</tr><tr><td>
	@NoRepositoryBean
	</td>
	<td>
	?
	</td>
	<td>
	JPA
	</td>
</tr><tr><td>
	@EnableJpaRepositories
	</td>
	<td>
	?
	</td>
	<td>
	JPA
	</td>
</tr><tr><td>
	@EntityScan
	</td>
	<td>
	1.4.0
	</td>
	<td>
	JPA
	</td>
</tr><tr><td>
	@Entity
	</td>
	<td>
	?
	</td>
	<td>
	JPA
	</td>
</tr><tr><td>
	@Embeddable
	</td>
	<td>
	?
	</td>
	<td>
	JPA
	</td>
</tr><tr><td>
	@MappedSuperclass 
	</td>
	<td>
	?
	</td>
	<td>
	JPA
	</td>
</tr><tr><td>
	@NodeEntity
	</td>
	<td>
	?
	</td>
	<td>
	Neo4j
	</td>
</tr><tr><td>
	@EnableNeo4jRepositories
	</td>
	<td>
	?
	</td>
	<td>
	Neo4j
	</td>
</tr><tr><td>
	@SolrDocument
	</td>
	<td>
	?
	</td>
	<td>
	Solr
	</td>
</tr><tr><td>
	@Document 
	</td>
	<td>
	?
	</td>
	<td>
	Elasticsearch
	</td>
</tr><tr><td>
	@ManagementContextConfiguration
	</td>
	<td>
	1.3.0
	</td>
	<td>
	Actuator模块
	</td>
</tr><tr><td>
	@ExportMetricWriter
	</td>
	<td>
	1.3.0
	</td>
	<td>
	Actuator模块
	</td>
</tr><tr><td>
	@ExportMetricReader
	</td>
	<td>
	1.3.0
	</td>
	<td>
	Actuator模块
	</td>
</tr><tr><td>
	@EnableMBeanExport
	</td>
	<td>
	3.2
	</td>
	<td>
	JMX Mbean启用
	</td>
</tr><tr><td>
	@ManagedResource
	</td>
	<td>
	1.2
	</td>
	<td>
	将<strong>类的所有实例</strong>标识为JMX受控资源
	</td>
</tr><tr><td>
	@ManagedOperation
	</td>
	<td>
	1.2
	</td>
	<td>
	将方法标识为JMX操作
	</td>
</tr><tr><td>
	@ManagedAttribute
	</td>
	<td>
	1.2
	</td>
	<td>
	将getter或者setter标识为部分JMX属性
	</td>
</tr><tr><td>
	@ManagedOperationParameter
	</td>
	<td>
	1.2
	</td>
	<td>
	定义操作参数说明
	</td>
</tr><tr><td>
	@ManagedOperationParameters
	</td>
	<td>
	1.2
	</td>
	<td>
	定义操作参数说明
	</td>
</tr><tr><td>
	@EnableSpringSecurity
	</td>
	<td>
	?
	</td>
	<td>
	开启spring seccurity支持
	</td>
</tr><tr><td>
	@EnableGlobalMethodSecurity
	</td>
	<td>
	?
	</td>
	<td>
	启用'basic'认证
	</td>
</tr><tr><td>
	@EnableAuthorizationServer 
	</td>
	<td>
	?
	</td>
	<td>
	oauth2 access tokens
	</td>
</tr><tr><td>
	@EnableOAuth2Client
	</td>
	<td>
	?
	</td>
	<td>
	安全
	</td>
</tr><tr><td>
	@EnableOAuth2Sso
	</td>
	<td>
	?
	</td>
	<td>
	安全
	</td>
</tr><tr><td>
	@EnableIntegration
	</td>
	<td>
	?
	</td>
	<td>
	启用spring-boot-starter-integration基于消息和其他传输协议的抽象,比如HTTP，TCP等
	</td>
</tr><tr><td>
	@EnableJms
	</td>
	<td>
	?
	</td>
	<td>
	开启JMS支持
	</td>
</tr><tr><td>
	@JmsListener
	</td>
	<td>
	?
	</td>
	<td>
	创建一个监听者端点,默认是支持事务性的
	</td>
</tr><tr><td>
	@EnableRabbit
	</td>
	<td>
	?
	</td>
	<td>
	开启Rabbit支持(AMQP)
	</td>
</tr><tr><td>
	@RabbitListener
	</td>
	<td>
	?
	</td>
	<td>
	创建一个监听者端点
	</td>
</tr><tr><td>
	@ContextConfiguration
	</td>
	<td>
	2.5
	</td>
	<td>
	集成化测试中定义加载及配置ApplicationContext。<br/>
	Spring 3.1之前,只有基于路径的资源位置(通常是XML配置文件)的支持，Spring 4.0.4
	</td>
</tr><tr><td>
	@SpringBootTest
	</td>
	<td>
	1.4.0
	</td>
	<td>
	Test模块，并添加<br/>@RunWith(SpringRunner.class)
	</td>
</tr><tr><td>
	@TestConfiguration
	</td>
	<td>
	1.4.0
	</td>
	<td>
	类似@Configuration
	</td>
</tr><tr><td>
	@TestComponent
	</td>
	<td>
	1.4.0
	</td>
	<td>
	类似@Component
	</td>
</tr><tr><td>
	@LocalServerPort 
	</td>
	<td>
	1.4.0
	</td>
	<td>
	用于注入测试用例实际使用的端口
	</td>
</tr><tr><td>
	@MockBean
	</td>
	<td>
	1.4.0
	</td>
	<td>
	需Mock的bean
	</td>
</tr><tr><td>
	@SpyBean
	</td>
	<td>
	1.4.0
	</td>
	<td>
	获得代理对象相当于Mockito.spy(MethodTest.class)
	</td>
</tr><tr><td>
	@JsonTest
	</td>
	<td>
	1.4.0
	</td>
	<td>
	测试对象JSON序列化和反序列化是否工作正常
	</td>
</tr><tr><td>
	@AutoConfigureJsonTesters
	</td>
	<td>
	1.4.0
	</td>
	<td>
	启用和配置的JSON的自动测试
	</td>
</tr><tr><td>
	@WebMvcTest
	</td>
	<td>
	1.4.0
	</td>
	<td>
	检测单个Controller是否工作正常
	</td>
</tr><tr><td>
	@AutoConfigureMockMvc
	</td>
	<td>
	1.4.0
	</td>
	<td>
	注解一个non-@WebMvcTest的类
	</td>
</tr><tr><td>
	@DataJpaTest 
	</td>
	<td>
	1.4.0
	</td>
	<td>
	JPA测试
	</td>
</tr><tr><td>
	@AutoConfigureTestEntityManager
	</td>
	<td>
	1.4.0
	</td>
	<td>
	JPA
	</td>
</tr><tr><td>
	@AutoConfigureTestDatabase  
	</td>
	<td>
	1.4.0
	</td>
	<td>
	真实DB/嵌入DB
	</td>
</tr><tr><td>
	@RestClientTest
	</td>
	<td>
	1.4.0
	</td>
	<td>
	针对rest客户端测试
	</td>
</tr><tr><td>
	@AutoConfigureRestDocs
	</td>
	<td>
	1.4.0
	</td>
	<td>
	开启了restdocs生成snippets文件，并指定了存放位置
	</td>
</tr></tbody></table>

#### 3.@ConfigurationProperties vs @Value？
<table><tbody><tr><td></td>
			<td>@ConfigurationProperties</td>
			<td>@Value</td>
		</tr><tr><td>功能</td>
			<td>批量注入配置文件中属性</td>
			<td>一个个指定</td>
		</tr><tr><td>松散绑定</td>
			<td>Yes</td>
			<td>No</td>
		</tr><tr><td>SpEL表达式</td>
			<td>No</td>
			<td>Yes</td>
		</tr><tr><td>JSR303数据校验</td>
			<td>Yes</td>
			<td>No</td>
		</tr><tr><td><strong>复杂类型封装</strong></td>
			<td>Yes</td>
			<td>No</td>
		</tr><tr><td><a href="https://docs.spring.io/spring-boot/docs/2.0.0.BUILD-SNAPSHOT/reference/html/configuration-metadata.html" rel="nofollow" target="_blank">Configuration Metadata</a><br>
			配置项友好提示</td>
			<td>Yes</td>
			<td>NO</td>
		</tr></tbody></table>
Relaxed（松散绑定）绑定，Environment属性名和bean属性名不需要精确匹配。<p/>
<table>
<tr><td>属性</td><td>说明</td><tr>
<tr><td>person.firstName</td><td>标准驼峰规则</td><tr>
<tr><td>person.first-name</td><td>虚线表示，推荐用于.properties和.yml文件中</td><tr>
<tr><td>person.first_name</td><td>下划线表示，用于.properties和.yml文件的可选格式</td><tr>
<tr><td>PERSON_FIRST_NAME </td><td>大写形式，使用系统环境变量时推荐</td><tr>
</table>

#### 4.@Configuration vs Auto-configuration？
@Configuration替代xml来定义BeanDefinition的一种手段。Auto-configuration也是定义BeanDefinition的一种手段，也就是spring spi（META-INF/spring.factories）读取出来的key为org.springframework.boot.autoconfigure.EnableAutoConfiguration的配置类，简称为EnableAutoConfiguration加载。<br/>
这两者的相同之处有：<br/>
1.都是使用@Configuration注解的类，这些类里都可以定义@Bean、@Import、@ImportResource。<br/>
2.都可以使用@Condition*来根据情况选择是否加载<br/>
而不同之处有：<br/>
1.加载方式不同：普通@Configuration则是通过扫描package path加载的，Auto-configuration的是通过读取classpath*:META-INF/spring.factories中EnableAutoConfiguration<br/>
2.加载顺序不同，普通@Configuration的加载永远在Auto-configuration之前<br/>
3.内部加载顺序可控上的不同：普通@Configuration则无法控制加载顺序，Auto-configuration可以使用@AutoConfigureOrder、@AutoConfigureBefore、@AutoConfigureAfter <br/>

#### 5.常用对象及源码分析系列
首先如果你对spring3.x或2.x系列源码不太熟悉，spring boot的源码可能对你而言非常难，不过你也大可不必为此而烦恼，本来度源码讲究的就是领悟力，不一定要全部掌握的。<br/>
	先了解这些吧：<br/>
	AbstractApplicationContext
	AnnotationConfigApplicationContext
	AnnotatedBeanDefinitionReader
	ClassPathBeanDefinitionScanner
	EventPublishingRunListener
	SimpleApplicationEventMulticaster
	ApplicationStartedEvent / ApplicationEnvironmentPreparedEvent / ApplicationFailedEvent
	再看spring boot相关的：
 	JarLaucher
 	Archive
	SpringApplication
	SpringFactoriesLoader
	ApplicationContextInitializer
	ConfigurationClassPostProcessor
	AnnotationConfigServletWebServerApplicationContext
	.....
- [Spring Boot 容器启动原理揭秘](https://blog.csdn.net/alex_xfboy/article/details/88194392)<br/>
- [spring boot @SpringBootApplication 的工作原理](https://blog.csdn.net/alex_xfboy/article/details/88359955)<br/>
- [Spring boot 源码分析-Conditional](https://blog.csdn.net/alex_xfboy/article/details/88208069)<br/>
#### 6.常见jar迁移  
	druid -> 			druid-spring-boot-starter
	mybatis-spring -> mybatis-spring-boot-starter:MybatisAutoConfiguration(自动注入@Mapper)
	jedis ->			pring-boot-starter-data-redis
	com.github.pagehelper:pagehelper
	.....
#### 7.配置属性系列
8. 配置属性系列[查看](https://blog.csdn.net/alex_xfboy/article/details/76685476#8.%20%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E7%B3%BB%E5%88%97)<br/>
- 8.1 server
- 8.1.1 server
- 8.1.2 cookie / session
- 8.1.3 tomcat
- 8.1.4 undertow
- 8.1.3 ssl
- 8.2 MVC
- 8.2.1 MVC
- 8.2.2 http
- 8.2.3 view
- 8.2.4 multipart
- 8.2.5 json
- 8.2.6 resource
- 8.2.7 messages
- 8.2.8 freemarker
- 8.2.9 velocity
- 8.2.10 thymeleaf
- 8.2.11 mustcache
- 8.2.12 jersey
- 8.2.13 mobile
- 8.2.14 groovy
- 8.3 datasource
- 8.3.1 datasource
- 8.3.2 JPA
- 8.3.3 jooq
- 8.3.4 h2
- 8.3.4 JTA
- 8.4 cache
- 8.5 redis
- 8.6 mongodb
- 8.7 springdata
- 8.8 mq
- 8.8.1 rabbitmq
- 8.8.2 activemq
- 8.8.3 jms
- 8.9 spring security
- 8.10 其他
- 8.10.1 aop
- 8.10.2 application
- 8.10.3 autoconfig
- 8.10.4 batch
- 8.10.5 jmx
- 8.10.6 mail
- 8.10.7 sendgrid
- 8.10.8 social
