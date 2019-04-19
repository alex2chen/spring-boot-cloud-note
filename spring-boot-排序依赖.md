springboot最频繁使用的属于java config(去xml)，而且是大量使用。最近在项目中最被搞晕的就是@AutoConfigureAfter之列的，感觉这个特别不好用(有时生效有时失效)，下面将透彻的探讨这个东西。

### 问题描述
一般的开发项目中使用如下：
```java
@Configuration
@AutoConfigureAfter(BatchConfiguration.class)
public class BatchImportConfiguration {
	...
}
```
你以为@AutoConfigureAfter有用，其实它能不能用完全是靠运气(不报错就不代表没问题)。

### 预测的结论
	Spring Boot提供了很多新的注解，但是要注意这个包(org.springframework.boot.autoconfigure)下面的所有注解：
	@AutoConfigureAfter：在指定的配置类初始化后再加载
	@AutoConfigureBefore：在指定的配置类初始化前加载
	@AutoConfigureOrder：数越小越先初始化
	@AutoConfigurationPackage
	@EnableAutoConfiguration
	@SpringBootApplication
	前3个是不能在普通项目中使用，这3个注解是特地用于autoconfigure 类的项目，后面3个注解是可以用于我们自己项目中。
	
###	autoconfigure类项目
	spring Boot Starter提供了规范可以让开发者提供自己的 starter，例如 Spring 官方提供的：
-   spring-boot-starter
-   spring-boot-starter-activemq
-   spring-boot-starter-cache
-   …
	<br/>还有很多开源项目或公司提供的：
-   hajdbc-spring-boot-starter
-   camel-spring-boot-starter
-   mybatis-spring-boot-starter
-   …
	<br/>在Spring Boot starter开发规范中中：
	xxx-spring-boot-starter项目，主要靠 pom.xml 将所有需要的依赖引入进来。
	xxx-spring-boot-autoconfigure项目，主要写带 @Configuration 注解的配置类，在这个类或者类中带 @Bean 的方法上，可以使用和顺序有关的注解，也就是前面提到的自己不能使用的这部分注解。
	上面的注解只在 AutoConfigurationSorter 类中排序时用到了。被排序的这些类都是通过xxx-spring-boot-autoconfigure项目中的 src/resources/META-INF/spring.factories 配置文件获取的，这个文件中的配置内容一般为：
	#Auto Configure
	org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration
	SpringBoot只会对从这个文件读取的配置类进行排序。但你不要以为只要把自己的配置类添加到spring.factories 中就可以完成实现排序，如果你的类被自己SpringBoot启动类扫描到，这个类的顺序会优先于所有通过spring.factories 读取的配置类。
### spring.factories巧用排序
	如果你将自己的配置类放到特别的包下，不使用SpringBoot启动类扫描(不在同级目录下)。完全通过spring.factories读取配置就可以实现这个目的。