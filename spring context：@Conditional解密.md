在Spring Boot中大量的应用到`@Conditional`条件注解，`Condition`接口是spring4.0增加的条件判断接口，用于判断条件满足情况，主要集中在Bean的定义和使用。当前文章基于spring boot 1.5.4.RELEASE，spring-core 4.3.9.RELEASE。

[toc]

### 1.what

通过`@Conditional`注解配合`Condition`接口，来决定给一个bean是否创建和注册到Spring容器中。这样做的好处是：

- 当有多个同名bean时选择的问题
- 解决某些bean的创建时依赖某些条件的场景

更多的真相要看java doc：

```java
//源于org.springframework.context.annotation;
/**
 * Indicates that a component is only eligible for registration when all
 * {@linkplain #value specified conditions} match.
 *
 * <p>A <em>condition</em> is any state that can be determined programmatically
 * before the bean definition is due to be registered (see {@link Condition} for details).
 *
 * <p>The {@code @Conditional} annotation may be used in any of the following ways:
 * <ul>
 * <li>as a type-level annotation on any class directly or indirectly annotated with
 * {@code @Component}, including {@link Configuration @Configuration} classes</li>
 * <li>as a meta-annotation, for the purpose of composing custom stereotype
 * annotations</li>
 * <li>as a method-level annotation on any {@link Bean @Bean} method</li>
 * </ul>
 *
 * <p>If a {@code @Configuration} class is marked with {@code @Conditional},
 * all of the {@code @Bean} methods, {@link Import @Import} annotations, and
 * {@link ComponentScan @ComponentScan} annotations associated with that
 * class will be subject to the conditions.
 *
 * <p><strong>NOTE</strong>: Inheritance of {@code @Conditional} annotations
 * is not supported; any conditions from superclasses or from overridden
 * methods will not be considered. In order to enforce these semantics,
 * {@code @Conditional} itself is not declared as
 * {@link java.lang.annotation.Inherited @Inherited}; furthermore, any
 * custom <em>composed annotation</em> that is meta-annotated with
 * {@code @Conditional} must not be declared as {@code @Inherited}.
 *
 * @since 4.0
 * @see Condition
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Conditional {
	/**
	 * All {@link Condition Conditions} that must {@linkplain Condition#matches match}
	 * in order for the component to be registered.
	 */
	Class<? extends Condition>[] value();
}
/**
 * A single {@code condition} that must be {@linkplain #matches matched} in order
 * for a component to be registered.
 *
 * <p>Conditions are checked immediately before the bean-definition is due to be
 * registered and are free to veto registration based on any criteria that can
 * be determined at that point.
 *
 * <p>Conditions must follow the same restrictions as {@link BeanFactoryPostProcessor}
 * and take care to never interact with bean instances. For more fine-grained control
 * of conditions that interact with {@code @Configuration} beans consider the
 * {@link ConfigurationCondition} interface.
 *
 * @since 4.0
 * @see ConfigurationCondition
 * @see Conditional
 * @see ConditionContext
 */
@FunctionalInterface
public interface Condition {
	boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata);
}
```

### 2.how

用法非常简单！

#### 2.1 example

比如：要实现在不同系统环境中创建监控服务MonitorService，还算比较简单（spring帮我们做了很多事情）。

```java
public class LinuxCondition implements Condition{
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {        
        return context.getEnvironment().getProperty("os.name").contains("Linux");
    }
}
public class WindowsCondition implements Condition{
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {    
        return context.getEnvironment().getProperty("os.name").contains("Windows");
    }    
}
public interface MonitorService {
    MonitorData ps();
}
public class LinuxService implements MonitorService {
    public MonitorData ps(){
        return ...;
    }    
}
public class WindowsService implements MonitorService {
    public String ps(){
        return ...;
    }    
}
@Configuration
public class MyConfiguration {
    @Bean(name = "monitor")
    @Conditional(WindowsCondition.class)
    public MonitorService windowsService() {
        return new WindowsService();
    }
 
    @Bean(name = "monitor")
    @Conditional(LinuxCondition.class)
    public MonitorService  linuxEmailerService() {
        return new LinuxService();
    }
}
```

当然如果你觉得再定义`MyConfiguration`太麻烦了，也可以用下面的写法，原理都是一样的。

```java
@Component
@Conditional(LinuxCondition.class)
public class LinuxService implements MonitorService {
    public MonitorData ps(){
        return ...;
    }    
}
```

#### 2.2 spring拓展集

| 注解							| 描述 																			 |
| ------------------------		| ------------------------------------------------------------------------------ |
|@ConditionalOnMissingBean		|当给定的类型、类名、注解、昵称在beanFactory中不存在时返回true.各类型间是or的关系|
|@ConditionalOnBean				|与上面相反，要求bean存在                                                        |
|@ConditionalOnMissingClass		|当给定的类名在类路径上不存在时返回true,各类型间是and的关系                      |
|@ConditionalOnClass			|与上面相反，要求类存在                                                          |
|@ConditionalOnProperty			|要求配置属性匹配条件                                                            |
|@ConditionalOnJava				|运行时的java版本号是否包含给定的版本号.如果包含,返回匹配,否则,返回不匹配        |
|@ConditionalOnExpression		|spel表达式执行为true                                                            |
|@ConditionalOnCloudPlatform	|当所配置的CloudPlatform为激活时返回true                                         |
|@ConditionalOnSingleCandidate 	|当给定类型的bean存在并且指定为Primary的给定类型存在时,返回true                  |
|@ConditionalOnJndi			    |给定的jndi的Location 必须存在一个.否则,返回不匹配                               |
|@ConditionalOnNotWebApplication|web环境不存在时                                                                 |
|@ConditionalOnWebApplication	|web环境存在时                                                                   |
|@ConditionalOnResource			|要求制定的资源存在                                                              |
### 3.why

#### 3.1 基本原理

其实这个特性实现不是特别复杂，如果你对spring的扫描工具`ClassPathScanningCandidateComponentProvider`有一定的了解，会比较好理解，在读取每个class时便会执行Condition逻辑，通过则可以获取到`BeanDefinition`

注：这里有个细节，读取class并不对加载这个class（spring的`ClassMetadata`加载规则可参与之前【**spring-core：元数据之AnnotationMetadata**】章节）

#### 3.2 ConditionContext

在上面我们看到过`ConditionContext`，它可以用来获取很多系统相关的信息，来丰富条件判断：

```java
// 源于org.springframework.context.annotation;
public interface ConditionContext {
	BeanDefinitionRegistry getRegistry();                //Bean定义
	ConfigurableListableBeanFactory getBeanFactory();    //Bean容器
	Environment getEnvironment();        //所有的配置信息
	ResourceLoader getResourceLoader();//资源信息
	ClassLoader getClassLoader();
}
// 实现不展开讲
class ConditionEvaluator {
	private static class ConditionContextImpl implements ConditionContext {
    }
}
```

#### 3.3 AnnotatedBeanDefinitionReader

`AnnotatedBeanDefinitionReader`管理`ConditionContext`，你可能不熟悉，不急！`AnnotationConfigApplicationContext` （这个总知道吧）是spring 3.0以后引入的类，用于处理spring注解。

```java
public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {
	public AnnotationConfigApplicationContext() {
		this.reader = new AnnotatedBeanDefinitionReader(this);//就是它
		this.scanner = new ClassPathBeanDefinitionScanner(this);//还有它
	}
}
public class AnnotatedBeanDefinitionReader {
	private ConditionEvaluator conditionEvaluator;
	public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry, Environment environment) {
		Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
		Assert.notNull(environment, "Environment must not be null");
		this.registry = registry;
                //实例化ConditionContext   
		this.conditionEvaluator = new ConditionEvaluator(registry, environment, null);
		AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);
	}
}
//不知道AnnotationConfigApplicationContext 是啥的看这里吧
public static void main(String[] args) {
    ApplicationContext context = newAnnotationConfigApplicationContext(Application.class);
    MessagePrinter printer =context.getBean(MessagePrinter.class);
    printer.printMessage();
}
```

到这里Condition来自哪里的问题基本上已经解决了，下一步就是看，它是如何调用的？

**AnnotatedBeanDefinitionReader.registerBean**

在注册bean的时候会拿Condition判断是否这个是一个满足条件应该注册的bean，看代码

```java
 /*
 * Register a bean from the given bean class, deriving its metadata from class-declared annotations.
 */
public void registerBean(Class<?> annotatedClass, String name, Class<? extends Annotation>... qualifiers) {
	AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(annotatedClass);
	//注解判断  true就不注册这个class了
	if (this.conditionEvaluator.shouldSkip(abd.getMetadata())) {
		return;
	}
 
	ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(abd);
	abd.setScope(scopeMetadata.getScopeName());
	String beanName = (name != null ? name : this.beanNameGenerator.generateBeanName(abd, this.registry));
	AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);
	if (qualifiers != null) {
		for (Class<? extends Annotation> qualifier : qualifiers) {
			if (Primary.class == qualifier) {
				abd.setPrimary(true);
			}
			else if (Lazy.class == qualifier) {
				abd.setLazyInit(true);
			}
			else {
				abd.addQualifier(new AutowireCandidateQualifier(qualifier));
			}
		}
	}
 
	BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
	//创建ScopedProxyMode代理
	definitionHolder = AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
	BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, this.registry);
}
```

#### 3.4 ClassPathScanningCandidateComponentProvider

`AnnotationConfigApplicationContext#scan`（ClassPathBeanDefinitionScanner）主要是借助`ClassPathScanningCandidateComponentProvider`。它不但可以被spring使用，我们也可以单独引用。

```java
//从类路径中扫描component
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
	protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Assert.notEmpty(basePackages, "At least one base package must be specified");
		Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<BeanDefinitionHolder>();
		for (String basePackage : basePackages) {
                        //看这里，扫描
			Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
			for (BeanDefinition candidate : candidates) {
				ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(candidate);
				candidate.setScope(scopeMetadata.getScopeName());
				String beanName = this.beanNameGenerator.generateBeanName(candidate, this.registry);
				if (candidate instanceof AbstractBeanDefinition) {
					postProcessBeanDefinition((AbstractBeanDefinition) candidate, beanName);
				}
				if (candidate instanceof AnnotatedBeanDefinition) {
					AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
				}
				if (checkCandidate(beanName, candidate)) {
					BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, beanName);
					definitionHolder =
							AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
					beanDefinitions.add(definitionHolder);
					registerBeanDefinition(definitionHolder, this.registry);
				}
			}
		}
		return beanDefinitions;
	}
}
public class ClassPathScanningCandidateComponentProvider implements EnvironmentCapable, ResourceLoaderAware {
	private boolean isConditionMatch(MetadataReader metadataReader) {
		if (this.conditionEvaluator == null) {
                //实例化ConditionContext
		this.conditionEvaluator = new ConditionEvaluator(getRegistry(), getEnvironment(), getResourceLoader());
		}
                //注解校验
		return !this.conditionEvaluator.shouldSkip(metadataReader.getAnnotationMetadata());
	}
	public Set<BeanDefinition> findCandidateComponents(String basePackage) {
		Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
		try {
			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
					resolveBasePackage(basePackage) + '/' + this.resourcePattern;
			Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
			boolean traceEnabled = logger.isTraceEnabled();
			boolean debugEnabled = logger.isDebugEnabled();
			for (Resource resource : resources) {
				if (traceEnabled) {
					logger.trace("Scanning " + resource);
				}
				if (resource.isReadable()) {
					try {
						MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                                                //满足是一个组件的所有要求
						if (isCandidateComponent(metadataReader)) {
                                                        //ScannedGenericBeanDefinition其实就是一个带有注解信息，没有任何构造函数参数等等的GenericBeanDefinition
							ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
							sbd.setResource(resource);
							sbd.setSource(resource);
                                                    //判断是否给定的beandefinition是一个具体的类，不是接口或者抽象类
							if (isCandidateComponent(sbd)) {
								if (debugEnabled) {
									logger.debug("Identified candidate component class: " + resource);
								}
								candidates.add(sbd);
							}
							else {
								if (debugEnabled) {
									logger.debug("Ignored because not a concrete top-level class: " + resource);
								}
							}
						}
						else {
							if (traceEnabled) {
								logger.trace("Ignored because not matching any filter: " + resource);
							}
						}
					}
					catch (Throwable ex) {
						throw new BeanDefinitionStoreException(
								"Failed to read candidate component class: " + resource, ex);
					}
				}
				else {
					if (traceEnabled) {
						logger.trace("Ignored because not readable: " + resource);
					}
				}
			}
		}
		catch (IOException ex) {
			throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
		}
		return candidates;
	}
	protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
		for (TypeFilter tf : this.excludeFilters) {
			if (tf.match(metadataReader, this.metadataReaderFactory)) {
				return false;
			}
		}
		for (TypeFilter tf : this.includeFilters) {
			if (tf.match(metadataReader, this.metadataReaderFactory)) {
				return isConditionMatch(metadataReader);
			}
		}
		return false;
	}
}
```

#### 3.5 ConfigurationClassPostProcessor

除了上面的用法外（不直接加载class），Spring按照`Configuration`类的方式进行配置的时候，会加载解析`Configuration`组件类，加载的时候会调用`Condition`的子类`ConfigurationCondition`去判断是否需要解析`Configuration`或者是否注册Bean（`ConfigurationClassPostProcessor`）。

首先还是从入口开始，`ConfigurationClassPostProcessor`的postProcessBeanFactory，首先调用`ConfigurationClassParser `的parse方法，再调用`ConfigurationClassBeanDefinitionReader`的loadBeanDefinitions方法完成依赖bean的判决及注入。

```java
public interface ConfigurationCondition extends Condition {
	ConfigurationPhase getConfigurationPhase();
	enum ConfigurationPhase {
		PARSE_CONFIGURATION,
		REGISTER_BEAN
	}
}
public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor,
		PriorityOrdered, ResourceLoaderAware, BeanClassLoaderAware, EnvironmentAware {
    //就是它
    private ConfigurationClassBeanDefinitionReader reader;
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
          if (!this.registriesPostProcessed.contains(factoryId)) {
			// BeanDefinitionRegistryPostProcessor hook apparently not supported...
			// Simply call processConfigurationClasses lazily at this point then.
			processConfigBeanDefinitions((BeanDefinitionRegistry) beanFactory);
		}        
    }
    public void processConfigBeanDefinitions(BeanDefinitionRegistry registry) {
        // Parse each @Configuration class
		ConfigurationClassParser parser = new ConfigurationClassParser(
				this.metadataReaderFactory, this.problemReporter, this.environment,
				this.resourceLoader, this.componentScanBeanNameGenerator, registry);
            parser.parse(candidates);
			parser.validate();
        // Read the model and create bean definitions based on its content
		if (this.reader == null) {
			this.reader = new ConfigurationClassBeanDefinitionReader(
						registry, this.sourceExtractor, this.resourceLoader, this.environment,
					this.importBeanNameGenerator, parser.getImportRegistry());
		}
		this.reader.loadBeanDefinitions(configClasses);
    }
}
class ConfigurationClassBeanDefinitionReader {
	private final ConditionEvaluator conditionEvaluator;
	/**
	 * Create a new {@link ConfigurationClassBeanDefinitionReader} instance that will be used
	 * to populate the given {@link BeanDefinitionRegistry}.
	 */
	ConfigurationClassBeanDefinitionReader(BeanDefinitionRegistry registry, SourceExtractor sourceExtractor,
			ResourceLoader resourceLoader, Environment environment, BeanNameGenerator importBeanNameGenerator,
			ImportRegistry importRegistry) {
 
		this.registry = registry;
		this.sourceExtractor = sourceExtractor;
		this.resourceLoader = resourceLoader;
		this.environment = environment;
		this.importBeanNameGenerator = importBeanNameGenerator;
		this.importRegistry = importRegistry;
		this.conditionEvaluator = new ConditionEvaluator(registry, environment, resourceLoader);
	}
	/**
	 * Read {@code configurationModel}, registering bean definitions
	 * with the registry based on its contents.
	 */
	public void loadBeanDefinitions(Set<ConfigurationClass> configurationModel) {
    }
}
class ConfigurationClassParser {
	private final ConditionEvaluator conditionEvaluator;
	public ConfigurationClassParser(MetadataReaderFactory metadataReaderFactory,
			ProblemReporter problemReporter, Environment environment, ResourceLoader resourceLoader,
			BeanNameGenerator componentScanBeanNameGenerator, BeanDefinitionRegistry registry) {
 
		this.metadataReaderFactory = metadataReaderFactory;
		this.problemReporter = problemReporter;
		this.environment = environment;
		this.resourceLoader = resourceLoader;
		this.registry = registry;
		this.componentScanParser = new ComponentScanAnnotationParser(
				environment, resourceLoader, componentScanBeanNameGenerator, registry);
		this.conditionEvaluator = new ConditionEvaluator(registry, environment, resourceLoader);
	}
	public void parse(Set<BeanDefinitionHolder> configCandidates) {
		this.deferredImportSelectors = new LinkedList<DeferredImportSelectorHolder>();
 
		for (BeanDefinitionHolder holder : configCandidates) {
			BeanDefinition bd = holder.getBeanDefinition();
			try {
				if (bd instanceof AnnotatedBeanDefinition) {
					parse(((AnnotatedBeanDefinition) bd).getMetadata(), holder.getBeanName());
				}
				else if (bd instanceof AbstractBeanDefinition && ((AbstractBeanDefinition) bd).hasBeanClass()) {
					parse(((AbstractBeanDefinition) bd).getBeanClass(), holder.getBeanName());
				}
				else {
					parse(bd.getBeanClassName(), holder.getBeanName());
				}
			}
			catch (BeanDefinitionStoreException ex) {
				throw ex;
			}
			catch (Throwable ex) {
				throw new BeanDefinitionStoreException(
						"Failed to parse configuration class [" + bd.getBeanClassName() + "]", ex);
			}
		}
 
		processDeferredImportSelectors();
	}
	protected final void parse(Class<?> clazz, String beanName) throws IOException {
		processConfigurationClass(new ConfigurationClass(clazz, beanName));
	}
	protected void processConfigurationClass(ConfigurationClass configClass) throws IOException {
		if (this.conditionEvaluator.shouldSkip(configClass.getMetadata(), ConfigurationPhase.PARSE_CONFIGURATION)) {
			return;
		}
	}
}
```

#### 3.6 SpringBootCondition

spring boot提供了`SpringBootCondition`（Spring Boot项目实现`Condition`的基础，它提供合理的日志记录以帮助用户诊断加载的类）注解来实现更丰富的内容。使用方式也特别简单，完全可以参考spring Condtion拓展集中的`@ConditionalOnJava`。

```java
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnClassCondition.class)//此次
public @interface ConditionalOnClass {
	Class<?>[] value() default {};
	String[] name() default {};
}
@Order(Ordered.HIGHEST_PRECEDENCE)
class OnClassCondition extends SpringBootCondition
		implements AutoConfigurationImportFilter, BeanFactoryAware, BeanClassLoaderAware {
}
```

这时候你可能会问`SpringBootCondition`和`ConfigurationCondition`有什么区别？

`ConfigurationCondition`为spring框架特有的规约接口（与`@Configuration`一起使用时提供更细粒度控制的条件），`SpringBootCondition`是一个实现类，说实话没可比性，拓展的时候建议使用它。

### 4.小结

spring中使用Conditional的用法及实现原理支持两种，一种是注册bean的时候拿Condition判断，另一种是需要解析Configuration的时候拿Condition判断。