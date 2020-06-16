总所周知，spring boot对各类日志组件进行了集成，使用起来非常便捷，让我们需要定义对应日志框架的配置文件，比如LogBack、Log4j2等，代码内部便可以直接使用。话不多说，接下来让我们来领略spring这块的奥秘吧。

[toc]

#### spring如何集成日志组件

猜想肯定是spring应用启动前已完成log组件的初始化工作？没错，spring boot中通过事件驱动，主要是借助了`ApplicationStartingEvent`（启动）以及`ApplicationEnvironmentPreparedEvent`（配置环境准备）来完成的。

入口在spring的SPI文件，spring-boot-2.1.3.RELEASE.jar/META-INF/spring.factories文件内容：

```properties
# Application Listeners
org.springframework.context.ApplicationListener=\
org.springframework.boot.ClearCachesApplicationListener,\
org.springframework.boot.builder.ParentContextCloserApplicationListener,\
org.springframework.boot.context.FileEncodingApplicationListener,\
org.springframework.boot.context.config.AnsiOutputApplicationListener,\
org.springframework.boot.context.config.ConfigFileApplicationListener,\
org.springframework.boot.context.config.DelegatingApplicationListener,\
org.springframework.boot.context.logging.ClasspathLoggingApplicationListener,\
org.springframework.boot.context.logging.LoggingApplicationListener,\
org.springframework.boot.liquibase.LiquibaseServiceLocatorApplicationListener
```

重点关注`LoggingApplicationListener`，而`ApplicationListener`想必就不陌生了，它的的初始化及触发点在于spring boot的start-class `SpringApplication#run`

```java
//源于package org.springframework.boot.context.logging;
public class LoggingApplicationListener implements GenericApplicationListener {
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ApplicationStartingEvent) {//先执行
			onApplicationStartingEvent((ApplicationStartingEvent) event);
		}
		else if (event instanceof ApplicationEnvironmentPreparedEvent) {//后执行
			onApplicationEnvironmentPreparedEvent(
					(ApplicationEnvironmentPreparedEvent) event);
		}
		else if (event instanceof ApplicationPreparedEvent) {
			onApplicationPreparedEvent((ApplicationPreparedEvent) event);
		}
		else if (event instanceof ContextClosedEvent && ((ContextClosedEvent) event)
				.getApplicationContext().getParent() == null) {
			onContextClosedEvent();
		}
		else if (event instanceof ApplicationFailedEvent) {
			onApplicationFailedEvent();
		}
	}
	private void onApplicationStartingEvent(ApplicationStartingEvent event) {
		this.loggingSystem = LoggingSystem
				.get(event.getSpringApplication().getClassLoader());//实例化loggingSystem
		this.loggingSystem.beforeInitialize();//loggingSystem初始化操作的前置处理
	}

	private void onApplicationEnvironmentPreparedEvent(
			ApplicationEnvironmentPreparedEvent event) {
		if (this.loggingSystem == null) {
			this.loggingSystem = LoggingSystem
					.get(event.getSpringApplication().getClassLoader());
		}
		initialize(event.getEnvironment(), event.getSpringApplication().getClassLoader());//loggingSystem初始化操作
	}
	private void onContextClosedEvent() {
		if (this.loggingSystem != null) {
			this.loggingSystem.cleanUp();
		}
	}

	private void onApplicationFailedEvent() {
		if (this.loggingSystem != null) {
			this.loggingSystem.cleanUp();
		}
	}
	protected void initialize(ConfigurableEnvironment environment,
			ClassLoader classLoader) {
		new LoggingSystemProperties(environment).apply();
		LogFile logFile = LogFile.get(environment);//这个后面讲解
		if (logFile != null) {
			logFile.applyToSystemProperties();
		}
		initializeEarlyLoggingLevel(environment);
		initializeSystem(environment, this.loggingSystem, logFile);
		initializeFinalLoggingLevels(environment, this.loggingSystem);
		registerShutdownHookIfNecessary(environment, this.loggingSystem);
	}
}
//源于package org.springframework.boot;
public class SpringApplication {
	public ConfigurableApplicationContext run(String... args) {//这个不陌生吧，spring boot的入口
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ConfigurableApplicationContext context = null;
		Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
		configureHeadlessProperty();
		SpringApplicationRunListeners listeners = getRunListeners(args);
		listeners.starting();//触发ApplicationStartingEvent事件
		try {
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(
					args);
			ConfigurableEnvironment environment = prepareEnvironment(listeners,
					applicationArguments);//触发ApplicationPreparedEvent事件
			configureIgnoreBeanInfo(environment);
			Banner printedBanner = printBanner(environment);
			context = createApplicationContext();
			exceptionReporters = getSpringFactoriesInstances(
					SpringBootExceptionReporter.class,
					new Class[] { ConfigurableApplicationContext.class }, context);
			prepareContext(context, environment, listeners, applicationArguments,
					printedBanner);
			refreshContext(context);
			afterRefresh(context, applicationArguments);
			stopWatch.stop();
			if (this.logStartupInfo) {
				new StartupInfoLogger(this.mainApplicationClass)
						.logStarted(getApplicationLog(), stopWatch);
			}
			listeners.started(context);
			callRunners(context, applicationArguments);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, listeners);
			throw new IllegalStateException(ex);
		}

		try {
			listeners.running(context);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, null);
			throw new IllegalStateException(ex);
		}
		return context;
	}    
}    
```

到这来你基本上已经可以一览它的原貌，而这里面的细节（功能特性）会一一展开。

#### LoggingSystem

`LoggingSystem`是spring通用日志组件的抽象，它支持4种类型的日志：

- Log：JavaLoggingSystem
- Log4j：Log4JLoggingSystem
- Log4j2：Log4J2LoggingSystem
- Logback：LogbackLoggingSystem

LoggingSystem是个抽象类，内部有这几个方法：

- beforeInitialize，日志系统初始化之前需要处理的事情。抽象方法，不同的日志架构进行不同的处理
- initialize，初始化日志系统
- cleanUp，日志系统的清除工作
- getShutdownHandler，返回一个Runnable用于当jvm退出的时候处理日志系统关闭后需要进行的操作，默认返回null，也就是什么都不做
- setLogLevel，抽象方法，用于设置对应logger的级别

```java
//源于package org.springframework.boot.logging;
public abstract class AbstractLoggingSystem extends LoggingSystem {
	@Override
	public void initialize(LoggingInitializationContext initializationContext,
			String configLocation, LogFile logFile) {
		if (StringUtils.hasLength(configLocation)) {// 如果传递了日志配置文件
			initializeWithSpecificConfig(initializationContext, configLocation, logFile);
			return;
		}
        //加载各种默认配置文件
		initializeWithConventions(initializationContext, logFile);
	}

	private void initializeWithSpecificConfig(
			LoggingInitializationContext initializationContext, String configLocation,
			LogFile logFile) {
        // 处理日志配置文件中的占位符
		configLocation = SystemPropertyUtils.resolvePlaceholders(configLocation);
		loadConfiguration(initializationContext, configLocation, logFile);
	}   
	private void initializeWithConventions(
			LoggingInitializationContext initializationContext, LogFile logFile) {
        //加载classpath下的默认的配置文件
		String config = getSelfInitializationConfig();
		if (config != null && logFile == null) {
			// self initialization has occurred, reinitialize in case of property changes
			reinitialize(initializationContext);
			return;
		}
		if (config == null) {//加载classpath下的默认的配置文件（仅包含spring）
			config = getSpringInitializationConfig();
		}
		if (config != null) {
			loadConfiguration(initializationContext, config, logFile);
			return;
		}
        // 还是没找到日志配置文件的话，调用loadDefaults抽象方法加载，让子类实现
		loadDefaults(initializationContext, logFile);
	}
	protected String getSelfInitializationConfig() {
		return findConfig(getStandardConfigLocations());
	}
	protected String getSpringInitializationConfig() {
		return findConfig(getSpringConfigLocations());
	}
	//findConfig有个特点就是找到第一个存在的立即返回
	private String findConfig(String[] locations) {
		for (String location : locations) {
			ClassPathResource resource = new ClassPathResource(location,
					this.classLoader);
			if (resource.exists()) {
				return "classpath:" + location;
			}
		}
		return null;
	}
    //将默认的配置文件中替换成-spring的配置文件
	protected String[] getSpringConfigLocations() {
		String[] locations = getStandardConfigLocations();
		for (int i = 0; i < locations.length; i++) {
			String extension = StringUtils.getFilenameExtension(locations[i]);
			locations[i] = locations[i].substring(0,
					locations[i].length() - extension.length() - 1) + "-spring."
					+ extension;
		}
		return locations;
	}
	protected abstract String[] getStandardConfigLocations();    
}
//源自package org.springframework.boot.logging;
public abstract class Slf4JLoggingSystem extends AbstractLoggingSystem {
}
public class LogbackLoggingSystem extends Slf4JLoggingSystem {
	@Override
	protected String[] getStandardConfigLocations() {
		return new String[] { "logback-test.groovy", "logback-test.xml", "logback.groovy",
				"logback.xml" };
	}
}    
```

#### LogFile是什么

在spring yml配置中提供了logging.file和logging.path的配置，而它正是作用于`LogFile`

```java
//源自package org.springframework.boot.logging;
public class LogFile {
	LogFile(String file, String path) {
		Assert.isTrue(StringUtils.hasLength(file) || StringUtils.hasLength(path),
				"File or Path must not be empty");
		this.file = file;
		this.path = path;
	}
	@Override
	public String toString() {
		if (StringUtils.hasLength(this.file)) {
			return this.file;
		}
		String path = this.path;
		if (!path.endsWith("/")) {
			path = path + "/";
		}
		return StringUtils.applyRelativePath(path, "spring.log");
	}
	public static LogFile get(PropertyResolver propertyResolver) {
		String file = propertyResolver.getProperty(FILE_PROPERTY);
		String path = propertyResolver.getProperty(PATH_PROPERTY);
		if (StringUtils.hasLength(file) || StringUtils.hasLength(path)) {
			return new LogFile(file, path);
		}
		return null;
	}
}    
```

这个配置导致了调用initialize方法的时候logFile存在，这样不止有ConsoleAppender，还有一个FileAppender，这个FileAppender对应的文件就是LogFile文件，也就是logging.file配置的日志文件。

从上面代码实现可以看出，我们如果配置了logging.path和logging.file，那么生效的只有logging.file配置。

其实个人觉得LogFile没啥用，你会脱落开源日志组件的控制。

#### LoggingSystem的实例化

`LoggingSystem`被实例化那个，这个很多人讲的不太对，默认它取得顺序是`LogbackLoggingSystem`>`Log4J2LoggingSystem`>`JavaLoggingSystem`，如果类存在就选择。

```java
//源自package org.springframework.boot.logging;
public abstract class LoggingSystem {
	static {
		Map<String, String> systems = new LinkedHashMap<>();
		systems.put("ch.qos.logback.core.Appender",
				"org.springframework.boot.logging.logback.LogbackLoggingSystem");
		systems.put("org.apache.logging.log4j.core.impl.Log4jContextFactory",
				"org.springframework.boot.logging.log4j2.Log4J2LoggingSystem");
		systems.put("java.util.logging.LogManager",
				"org.springframework.boot.logging.java.JavaLoggingSystem");
		SYSTEMS = Collections.unmodifiableMap(systems);
	}
	public static LoggingSystem get(ClassLoader classLoader) {
		String loggingSystem = System.getProperty(SYSTEM_PROPERTY);
		if (StringUtils.hasLength(loggingSystem)) {//你也通过org.springframework.boot.logging.LoggingSystem来特殊指定
			if (NONE.equals(loggingSystem)) {
				return new NoOpLoggingSystem();
			}
			return get(classLoader, loggingSystem);
		}
		return SYSTEMS.entrySet().stream()
				.filter((entry) -> ClassUtils.isPresent(entry.getKey(), classLoader))
				.map((entry) -> get(classLoader, entry.getValue())).findFirst()
				.orElseThrow(() -> new IllegalStateException(
						"No suitable logging system located"));//取第一个
	}    
}    
```

#### logback.xml的加载优先级

其实从上面的代码`AbstractLoggingSystem#initializeWithConventions`中已经可以看出，它默认的加载顺序为：

- logback-test.groovy
- logback-test.xml
- logback.groovy
- logback.xml
- logback-test-spring.groovy
- logback-test-spring.xml
- logback-spring.groovy
- logback-spring.xml

#### logback-spring.xml的spring环境及变量

很多使用logback-spring.xml的同学大多比较在意可以使用<springProperty/>和<springProfile/>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="false">
    <property name="LOG_HOME" value="/log"/>
    <conversionRule conversionWord="ipandhostname" converterClass="com.yonghui.logback.IpConvert"/>

    <!--
        1. 文件的命名和加载顺序
           logback.xml早于application.yml加载，logback-spring.xml晚于application.yml加载
           如果logback配置需要使用application.yml中的属性，需要命名为logback-spring.xml
        2. logback使用application.yml中的属性
           使用springProperty才可使用application.yml中的值 可以设置默认值
    -->
    <springProperty scope="context" name="projectName" source="spring.project.name"/>
    <springProperty scope="context" name="appName" source="spring.application.name"/>
    <springProperty scope="context" name="appDev" source="spring.profiles.active"/>
    <springProperty scope="context" name="kafkaTopic" source="logback.kafka.topic"/>
    <springProperty scope="context" name="kafkaServers" source="logback.kafka.servers"/>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.core.encoder.LayoutWrappingEncoder">
            <layout class="org.apache.skywalking.apm.toolkit.log.logback.v1.x.TraceIdPatternLogbackLayout">
                <!-- 时间|环境 |项目名称 |应用名称|错误级别|ip|hostname|[%thread]| %logger{50}| %msg%n -->
                <pattern>%d{yyyy-MM-dd HH:mm:ss SSS}|${appDev}|${projectName}|${appName}|%-5level|%ipandhostname|[%thread]|%logger{50}|%tid|%msg%n
                </pattern>
            </layout>
        </encoder>
    </appender>
   <logger name="org.apache.kafka.clients.NetworkClient" level="error"/>
   <logger name="c.c.f.apollo.internals.RemoteConfigLongPollService" level="error"/>

    <springProfile name="SIT">
        <root level="info">
            <appender-ref ref="STDOUT"/>
            <appender-ref ref="ASYNC"/>
        </root>
    </springProfile>

    <springProfile name="UAT">
        <root level="info">
            <appender-ref ref="STDOUT"/>
            <appender-ref ref="ASYNC"/>
        </root>
    </springProfile>

    <springProfile name="PRO">
        <root level="info">
            <appender-ref ref="ASYNC"/>
        </root>
    </springProfile>
</configuration>    
```

它的底层实现其实也不复杂， 如果配置文件是xml，解析时借助日志组件的拦截（解析spring环境信息）

```java
//源自package org.springframework.boot.logging.logback;
public class LogbackLoggingSystem extends Slf4JLoggingSystem {
	//AbstractLoggingSystem#initializeWithConventions中被调用（config找到第一件事就是干这个） 
	@Override
	protected void loadConfiguration(LoggingInitializationContext initializationContext,
			String location, LogFile logFile) {
		super.loadConfiguration(initializationContext, location, logFile);
		LoggerContext loggerContext = getLoggerContext();
		stopAndReset(loggerContext);
		try {
			configureByResourceUrl(initializationContext, loggerContext,
					ResourceUtils.getURL(location));//重点关注这个
		}
		catch (Exception ex) {
			throw new IllegalStateException(
					"Could not initialize Logback logging from " + location, ex);
		}
		List<Status> statuses = loggerContext.getStatusManager().getCopyOfStatusList();
		StringBuilder errors = new StringBuilder();
		for (Status status : statuses) {
			if (status.getLevel() == Status.ERROR) {
				errors.append((errors.length() > 0) ? String.format("%n") : "");
				errors.append(status.toString());
			}
		}
		if (errors.length() > 0) {
			throw new IllegalStateException(
					String.format("Logback configuration error detected: %n%s", errors));
		}
	}

	private void configureByResourceUrl(
			LoggingInitializationContext initializationContext,
			LoggerContext loggerContext, URL url) throws JoranException {
		if (url.toString().endsWith("xml")) {//如果是xml，加载spring环境
			JoranConfigurator configurator = new SpringBootJoranConfigurator(
					initializationContext);
			configurator.setContext(loggerContext);
			configurator.doConfigure(url);//触发的GenericConfigurator.doConfigure（通过拦截器解析xml部分spring配置）
		}
		else {
			new ContextInitializer(loggerContext).configureByResource(url);
		}
	}    
}
//源自package org.springframework.boot.logging.logback;
class SpringBootJoranConfigurator extends JoranConfigurator {

	private LoggingInitializationContext initializationContext;

	SpringBootJoranConfigurator(LoggingInitializationContext initializationContext) {
		this.initializationContext = initializationContext;
	}

	@Override
	public void addInstanceRules(RuleStore rs) {
		super.addInstanceRules(rs);
		Environment environment = this.initializationContext.getEnvironment();
		rs.addRule(new ElementSelector("configuration/springProperty"),
				new SpringPropertyAction(environment));
		rs.addRule(new ElementSelector("*/springProfile"),
				new SpringProfileAction(environment));
		rs.addRule(new ElementSelector("*/springProfile/*"), new NOPAction());
	}

}
```

至此，你可能再也不会纠结于spring中日志系统何时被加载，何时被卸载。