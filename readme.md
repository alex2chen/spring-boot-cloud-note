Spring Boot是由Pivotal团队提供的全新框架，其设计目的是用来简化新Spring应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。它已经成为地球上最热门的技术之一。<p/>

直接看[spring cloud笔记](https://github.com/alex2chen/spring-boot-cloud-note/blob/master/spring%20cloud%20note.md)<br/>

### 推荐图书
- Java EE开发的颠覆者 Spring Boot实战
- Spring Boot参考指南

#### 1.application.yml和bootstrap.yml区别？
bootstrap.yml可以理解成系统级别的一些参数配置，这些参数一般是不会变动的<br/>
application.yml 可以用来定义应用级别的，如果搭配spring-cloud-config使用 application.yml里面定义的文件可以实现动态替换
#### 2.springboot新增改进注解
<table border="1" cellspacing="0"><tbody><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><strong>注解</strong></p>
	</td>
	<td style="width:32.1pt;">
	<p style="margin-left:0cm;"><strong>版本</strong></p>
	</td>
	<td style="width:191.7pt;">
	<p style="margin-left:0cm;"><strong>描述</strong></p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><s><span style="color:#ed7d31;">@Resource&nbsp;</span></s></p>
	</td>
	<td style="width:32.1pt;">
	<p style="margin-left:0cm;"><s>-</s></p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;"><s><span style="color:#333333;">JavaEE5</span></s><s><span style="color:#333333;">规范</span></s></p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><s><span style="color:#ed7d31;">@PostConstruct</span></s></p>
	</td>
	<td style="width:32.1pt;">
	<p style="margin-left:0cm;"><s>-</s></p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;"><s><span style="color:#333333;">JavaEE5</span></s><s><span style="color:#333333;">规范</span></s></p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><s><span style="color:#ed7d31;">@PreDestroy</span></s></p>
	</td>
	<td style="width:32.1pt;">
	<p style="margin-left:0cm;"><s>-</s></p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;"><s><span style="color:#333333;">JavaEE5</span></s><s><span style="color:#333333;">规范</span></s></p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Autowired&nbsp;</span></p>
	</td>
	<td style="width:32.1pt;">
	<p style="margin-left:0cm;">2.5</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Qualifier</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">2.5</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">新改进特性：可以单独在方法中使用</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Required</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">2.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Scope</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">2.5</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Value</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@PropertySource</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">加载属性文件,绑定值,必须使用properties文件</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@PropertySources</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">PropertySource的集合形式</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Repository&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">2.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Service</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">2.5</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Component&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; </span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">2.5</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Bean</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">生产bean</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@DependsOn</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">控制依赖bean加载顺序</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Lazy</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">Bean懒加载</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Import</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">可导入Configuration、component classes、</p>

	<p style="margin-left:0cm;">ImportSelector、ImportResource</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ImportResource</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">导入XML配置文件</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Primary</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">在自动装配时这个bean应该优先当多个候选人资格（相同类型的bean）</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ComponentScan&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">扫描包，可定义@Filter</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Filter</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">扫描包过滤配置</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Configuration</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">表明这个是可以声明一个或多个@Bean的配置类</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ActiveProfiles&nbsp;&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">单元测试中指定环境变量</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Profile</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">多环节</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@SpringBootApplication&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.2.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">表明这是一个spring-boot启动类，等价于@Configuration,@ComponentScan,</p>

	<p style="margin-left:0cm;">@EnableAutoConfiguration</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@SpringBootConfiguration</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">表明这个类提供了spring-boot启动应用程序</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@EnableAutoConfiguration</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">启用自动配置Spring应用程序上下文，它自动根据您的类路径来获取spi（spring.factories）中这些的bean的定义。</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ConfigurationProperties</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">绑定属性值,适用于类或方法(第三方组件),支持javax.validation注解校验</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@EnableConfigurationProperties&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">开启对@ConfigurationProperties注解配置Bean的支持</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ConfigurationPropertiesBinding</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">ConfigurationProperties绑定属性时属性转换</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@AutoConfigureAfter</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">&nbsp;auto-configuration</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@AutoConfigureBefore</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">&nbsp;auto-configuration</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@AutoconfigureOrder</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">&nbsp;auto-configuration</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Conditional&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">该类下面的所有@Bean都会启用配置</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ConditionalOnClass</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">某个class位于类路径上，才会实例化一个Bean</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ConditionalOnMissingClass</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">某个class类路径上不存在的时候，才会实例化一个Bean</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ConditionalOnBean</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">Bean条件</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ConditionalOnMissingBean&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">反之</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ConditionalOnProperty&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.1.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">Property条件</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ConditionalOnResource</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">Resource条件</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ConditionalOnWebApplication&nbsp;&nbsp; </span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">WebApplication条件</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ConditionalOnNotWebApplication</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">反之</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ConditionalOnExpression&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">SpEL表达式条件</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ConditionalOnJndi</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.2.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">基于JNDI的可用性相匹</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ConditionalOnJava</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.1.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">指定JVM版本</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ConditionalOnCloudPlatform</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.5.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">指定的云平台</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@EnableAsync</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">异步任务的启用</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@Async</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">异步任务方法或者异步类</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@EnableScheduling</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">计划任务的启用</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Scheduled</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">声明这是一个计划任务，方法返回类型为void，支持cron、fixDelay、fixRate</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@EnableWebSocketMessagetBroker</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">webSocket启用</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@MessageMapping&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">websocket</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@EnableAspectJAutoProxy</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">AOP启用</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><s><span style="color:#ed7d31;">@Aspect</span></s></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;"><s>-</s></p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;"><s>aspectj</s></p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><s><span style="color:#ed7d31;">@PointCut</span></s></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;"><s>-</s></p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;"><s>aspectj</s></p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><s><span style="color:#ed7d31;">@Before</span></s></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;"><s>-</s></p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;"><s>aspectj</s></p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><s><span style="color:#ed7d31;">@After</span></s></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;"><s>-</s></p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;"><s>aspectj</s></p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><s><span style="color:#ed7d31;">@Around</span></s></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;"><s>-</s></p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;"><s>aspectj</s></p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><s><span style="color:#ed7d31;">@AfterReturning</span></s></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;"><s>-</s></p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;"><s>aspectj</s></p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><s><span style="color:#ed7d31;">@AfterThrowing</span></s></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;"><s>-</s></p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;"><s>aspectj</s></p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@EnableCaching&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">缓存启用</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@Cacheable</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">对其结果进行缓存</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@Caching</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">组合多个Cache注解使用</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@CacheEvict&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">根据一定的条件对缓存进行清空</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@CachePut&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; </span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">和 @Cacheable 不同的是，它每次执行前不会去检查缓存,都会触发真实方法的调用</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@CacheConfig&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">4.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">类级别的注解,对词重复（方法多次指定cacheNames的）定义的改进</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@Controller&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">2.5</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@RequestMapping&nbsp;&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">2.5</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ControllerAdvice</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.2</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">方法将应用于所有控制器，可以结合@ExceptionHandler、@InitBinder、@ModelAttribute使用</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@RestControllerAdvice</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">4.3</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">为ControllerAdvice和ResponseBody的组合体</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@CookieValue</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@SessionAttributes</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">2.5</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@SessionAttribute</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">4.3</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">将方法参数绑定到一个会话属性</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@CrossOrigin &nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">4.2</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">ajax跨域</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ExceptionHandler</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">处理异常的具体处理程序类和/或处理程序方法</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ResponseStatus</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">标志着一个方法或异常类状态应该返回</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@MatrixVariable</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.2</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">矩阵变量绑定参数(Map型参数)</p>

	<p style="margin-left:0cm;">如：/path;name=value;name2=value2</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@InitBinder</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">2.5</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">WebDataBinder用来自动绑定前台参数到mdel中</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ModelAttribute&nbsp;&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">2.5</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@RequestMapping&nbsp;&nbsp; &nbsp;&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">2.5</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@RequestAttribute</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">4.3</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">将方法参数绑定到请求属性</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@RequestParam&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; </span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">2.5</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@PathVariable&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; </span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@RequestBody&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ResponseBody&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; </span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@RequestHeader&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@RequestPart</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">绑定“multipart/form-data”参数</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@WebAppConfiguration</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.2</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">@WebAppConfiguration</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@EnableWebMvc&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">开启Web Mvc支持</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@RestController</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">为@Controller和@ResponseBody组合体</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ServletComponentScan</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">注入@WebServlet,@WebFilter,</p>

	<p style="margin-left:0cm;">@WebListener的类</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@GetMapping</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">4.3</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">组合注解,是@RequestMapping(method = RequestMethod.GET)的缩写</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@PostMapping</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">4.3</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">同上</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@PutMapping </span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">4.3</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">同上</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@DeleteMapping&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">4.3</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">同上</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@PatchMapping</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">4.3</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">同上</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@JsonComponent</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">自定义JSON序列化器和反序列化器</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@EnableTransactionManagement</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.1</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">事务启用</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@Transactional</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.2</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">略</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@NoRepositoryBean</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">JPA</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@EnableJpaRepositories</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">JPA</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@EntityScan</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">JPA</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@Entity</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">JPA</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@Embeddable</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">JPA</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@MappedSuperclass </span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">JPA</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@NodeEntity</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">&nbsp;Neo4j</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@EnableNeo4jRepositories</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">&nbsp;Neo4j</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@SolrDocument</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">&nbsp;Solr</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@Document </span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">&nbsp;Elasticsearch</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ManagementContextConfiguration&nbsp;&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">Actuator模块</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ExportMetricWriter</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">Actuator模块</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ExportMetricReader</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.3.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">Actuator模块</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@EnableMBeanExport</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">3.2</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">JMX Mbean启用</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@ManagedResource</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.2</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">将<strong>类的所有实例</strong>标识为JMX受控资源</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@ManagedOperation</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.2</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">将方法标识为JMX操作</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@ManagedAttribute</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.2</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">将getter或者setter标识为部分JMX属性</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@ManagedOperationParameter</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.2</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">定义操作参数说明</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@ManagedOperationParameters</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.2</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">定义操作参数说明</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@EnableSpringSecurity</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">开启spring seccurity支持</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@EnableGlobalMethodSecurity</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">启用'basic'认证</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@EnableAuthorizationServer </span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">oauth2 access tokens</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@EnableOAuth2Client</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">安全</p>
	</td>
</tr><tr><td style="vertical-align:top;width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@EnableOAuth2Sso</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">安全</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@EnableIntegration</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">启用spring-boot-starter-integration基于消息和其他传输协议的抽象,比如HTTP，TCP等</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@EnableJms</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">开启JMS支持</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@JmsListener</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">创建一个监听者端点,默认是支持事务性的</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@EnableRabbit&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">开启Rabbit支持(AMQP)</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#70ad47;">@RabbitListener</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">?</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">创建一个监听者端点</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@ContextConfiguration</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">2.5</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">集成化测试中定义加载及配置ApplicationContext。</p>

	<p style="margin-left:0cm;">Spring 3.1之前,只有基于路径的资源位置(通常是XML配置文件)的支持，Spring 4.0.4</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@SpringBootTest&nbsp;&nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">Test模块，并添加</p>

	<p style="margin-left:0cm;">@RunWith(SpringRunner.class)&nbsp;&nbsp; &nbsp;</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@TestConfiguration</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">类似@Configuration</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@TestComponent</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">类似@Component</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@LocalServerPort </span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">用于注入测试用例实际使用的端口</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@MockBean</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">需Mock的bean</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@SpyBean</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">获得代理对象相当于</p>

	<p style="margin-left:0cm;">Mockito.spy(MethodTest.class)</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@JsonTest</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">测试对象JSON序列化和反序列化是否工作正常</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@AutoConfigureJsonTesters</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">启用和配置的JSON的自动测试</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@WebMvcTest</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">检测单个Controller是否工作正常</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@AutoConfigureMockMvc</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">注解一个non-@WebMvcTest的类</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@DataJpaTest </span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">JPA测试</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@AutoConfigureTestEntityManager</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">JPA</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@AutoConfigureTestDatabase &nbsp;&nbsp; &nbsp;</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">真实DB/嵌入DB</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@RestClientTest</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">针对rest客户端测试</p>
	</td>
</tr><tr><td style="width:169.95pt;">
	<p style="margin-left:0cm;"><span style="color:#ed7d31;">@AutoConfigureRestDocs</span></p>
	</td>
	<td style="vertical-align:top;width:32.1pt;">
	<p style="margin-left:0cm;">1.4.0</p>
	</td>
	<td style="vertical-align:top;width:191.7pt;">
	<p style="margin-left:0cm;">开启了restdocs生成snippets文件，并指定了存放位置</p>
	</td>
</tr></tbody></table>

#### 3.@ConfigurationProperties vs @Value？
<table border="1" cellpadding="1" cellspacing="1"><tbody><tr><td style="width:186px;">&nbsp;</td>
			<td style="width:121px;">@ConfigurationProperties</td>
			<td style="width:542px;">@Value</td>
		</tr><tr><td style="width:186px;">功能</td>
			<td style="width:121px;">批量注入配置文件中属性</td>
			<td style="width:542px;">一个个指定</td>
		</tr><tr><td style="width:186px;">松散绑定</td>
			<td style="width:121px;">Yes</td>
			<td style="width:542px;">No</td>
		</tr><tr><td style="width:186px;">SpEL表达式</td>
			<td style="width:121px;">No</td>
			<td style="width:542px;">Yes</td>
		</tr><tr><td style="width:186px;">JSR303数据校验</td>
			<td style="width:121px;">Yes</td>
			<td style="width:542px;">No</td>
		</tr><tr><td style="width:186px;"><strong>复杂类型封装</strong></td>
			<td style="width:121px;">Yes</td>
			<td style="width:542px;">No</td>
		</tr><tr><td style="width:186px;"><a href="https://docs.spring.io/spring-boot/docs/2.0.0.BUILD-SNAPSHOT/reference/html/configuration-metadata.html" rel="nofollow" target="_blank">Configuration Metadata</a><br>
			配置项友好提示</td>
			<td style="width:121px;">Yes</td>
			<td style="width:542px;">NO</td>
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
