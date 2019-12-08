spring boot中有大量Starters 存在，正是Starters 存在让spring boot更具拓展性和威力。`spring-boot-starter-parent`项目声明定义了多个Starters，降低spring boot应用管理依赖的成本，而你的maven项目完全可以继承它，作为父POM。

**Starters**

> Starters是一组方便的依赖关系描述符，可以包含在应用程序中.您可以一站式地获得所需的所有Spring和相关技术，而无需搜索示例代码和复制粘贴的依赖描述符。例如，如果希望开始使用Spring和JPA进行数据库访问，请在项目中包含`spring-boot-starter-data-jpa`依赖项。[参与官方](https://docs.spring.io/spring-boot/docs/2.1.10.RELEASE/reference/html/using-boot-build-systems.html#using-boot-starter)

说白了Starters 您通过管理你项目中快速启动需要的依赖项（组件）的令牌，有了它让你更好的管理各个组件。

<font color=#ff000>注：starters 的命名也有规约，spring官方提供的starters 命名模式遵循spring-boot-starter-*。而你自己或别人创建的三方starters不能以spring-boot开头命名（它官方的Spring Boot工件保留的），通常以项目名称开头，如：thirdpartyproject-spring-boot-starter</font>

作为parent依赖的方式

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.example</groupId>
	<artifactId>myproject</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.10.RELEASE</version>
	</parent>
	<!-- Package as an executable jar -->
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
</project>    
```

同时，有时候你所在的组织不允许parent的方式，你也可以单独导入pom的方式:

```xml
<dependencyManagement>
	<dependencies>
		<dependency>
			<!-- Import dependency management from Spring Boot -->
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-dependencies</artifactId>
			<version>2.1.10.RELEASE</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
```

`spring-boot-starter-parent`和`spring-boot-dependencies`的关系

稍等细心的童鞋会发现`spring-boot-dependencies`是`spring-boot-starter-parent`的parent。

而`spring-boot-maven-plugin`的作用在于将当前的jar/war，打包成一个可执行的jar，此时`java -jar`即可运行，那此时你会问如果不添加此plugin就不可以运行吗?spring早都考虑了这点，可能在开发模式下的要求，通过命令 `mvn spring-boot:run`命令。

spring boot充分利用了maven的依赖管理特性，而非spring boot的专属。