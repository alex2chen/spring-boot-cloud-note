在互联网场景中，与终端用户交互的应用大多数是Web应用，其通信协议基本上是HTTP。而java圈中的选择无非就是Servlet和其他，前者几乎是垄断了java web开发，Tomcat 和Jetty作为Servlet的经典实现，后来者Undertow作为JBoss社区推出基于3.1+的新一代嵌入式容器。

嵌入式容器其实也并非spring boot的专属，早在Tomcat 5.x和Jetty 5.x就已经支持嵌入式容器。

关于嵌入式容器，官方介绍是这样的：

> Embed Tomcat, Jetty or Undertow directly (no need to deploy WAR files).
>
> Spring Boot includes support for embedded Tomcat, Jetty, and Undertow servers. Most developers will simply use the appropriate ‘Starter’ to obtain a fully configured instance.

| Servlet Version | Tomcat | Jetty | Undertow |
| --------------- | ------ | ----- | -------- |
| 2.5             | 6.x    | 6.x   | --       |
| 3.0             | 7.x    | 7.x   | --       |
| 3.1             | 8.x    | 8.x   | 1.4      |
| 4.0             | 9.x    | 9.x   | 2.x      |

Spring Boot 1.x 兼容 Servlet 和 Java 的版本更低一些，即 Servlet 3.0+ 和 Java 6+。它的Bean为`EmbeddedServletContainer`其中tomcat的实现类为：`TomcatEmbeddedServletContainer`

而 Spring Boot 2.0 延续了 Spring Boot 1.5 支持三种嵌入式 Servlet 容器类型的特性。

| Servlet容器类型 | WebServer接口       | WebServer工厂实现类               |
| --------------- | ------------------- | --------------------------------- |
| Tomcat          | `TomcatWebServer`   | `TomcatServletWebServerFactory`   |
| Jetty           | `JettyWebServer`    | `JettyServletWebServerFactory`    |
| Undertow        | `UndertowWebServer` | `UndertowServletWebServerFactory` |

#### Embedded Servlet Container

- 29.4. [Embedded Servlet Container Support](https://docs.spring.io/spring-boot/docs/2.1.10.RELEASE/reference/html/boot-features-developing-web-applications.html#boot-features-embedded-container)
- 29.4.1. [Servlets, Filters, and listeners](https://docs.spring.io/spring-boot/docs/2.1.10.RELEASE/reference/html/boot-features-developing-web-applications.html#boot-features-embedded-container)
- 29.4.2. Servlet Context Initialization
- 29.4.3. The ServletWebServerApplicationContext
- 29.4.4. Customizing Embedded Servlet Containers
- 29.4.5. JSP Limitations

#### Tomcat

可以从[官方Downloads](https://tomcat.apache.org/download-80.cgi)中下载Embedded zip或tar.gz。

- [tar.gz](http://mirrors.tuna.tsinghua.edu.cn/apache/tomcat/tomcat-8/v8.5.49/bin/embed/apache-tomcat-8.5.49-embed.tar.gz) ([pgp](https://www.apache.org/dist/tomcat/tomcat-8/v8.5.49/bin/embed/apache-tomcat-8.5.49-embed.tar.gz.asc), [sha512](https://www.apache.org/dist/tomcat/tomcat-8/v8.5.49/bin/embed/apache-tomcat-8.5.49-embed.tar.gz.sha512))
- [zip](http://mirrors.tuna.tsinghua.edu.cn/apache/tomcat/tomcat-8/v8.5.49/bin/embed/apache-tomcat-8.5.49-embed.zip) ([pgp](https://www.apache.org/dist/tomcat/tomcat-8/v8.5.49/bin/embed/apache-tomcat-8.5.49-embed.zip.asc), [sha512](https://www.apache.org/dist/tomcat/tomcat-8/v8.5.49/bin/embed/apache-tomcat-8.5.49-embed.zip.sha512))

将这些jar包直接引用你的Servlet web应用，我们注意到8.5.49的包，已经压缩6MB，而spring boot就是把这些嵌入式的容器加以继承。

其实早在[2012年-2月~2013年11月](http://tomcat.apache.org/maven-plugin.html)时 Tomcat 就已经提供了Maven Plugin，用于快速开发Servlet Web应用，它不需要编码，也不需要外置Tomcat容器，可以将你当前的应用直接打包为可执行的JAR或WAR文件，通过`java -jar`命令启动。

官方仅支持了`tomcat6-maven-plugin`和`tomcat7-maven-plugin`，而`tomcat8-maven-plugin`由第三方社区维护。

```xml
<plugin>
  <groupId>org.apache.tomcat.maven</groupId>
  <artifactId>tomcat7-maven-plugin</artifactId>
  <version>2.2</version>
  <executions>
    <execution>
      <id>tomcat-run</id>
      <goals>
        <goal>exec-war-only</goal>
      </goals>
      <phase>package</phase>
      <configuration>
        <path>foo</path>
        <!-- optional, needed only if you want to use a preconfigured server.xml file -->
        <serverXml>src/main/tomcatconf/server.xml</serverXml>
        <!-- optional values which can be configurable -->
        <attachArtifactClassifier>default value is exec-war but you can customize</attachArtifactClassifier>
        <attachArtifactClassifierType>default value is jar</attachArtifactClassifierType>
      </configuration>
    </execution>
  </executions>
</plugin>
```

tomcat定义的FAT JAR、FAT WAR的特点：

1. 仍然利用了传统Tomcat容器部署的方式（直接解压会发现目录层次结构很熟悉），将Web应用打包为Root.war，然后在Tomcat应用启动过程中将Root.war文件解压到webappps目录
2. JAR/WAR是一个归档文件，存在压缩的情况，传统的Servlet容器要将先压缩的文件解压到对应的目录后再加载其中的资源

> jar -0 归档命令
>
> ```html
> 用法: jar {ctxui}[vfmn0Me] [jar-file] [manifest-file] [entry-point] [-C dir] files ...
> 选项:
>     -c  创建新档案
>     -t  列出档案目录
>     -x  从档案中提取指定的 (或所有) 文件
>     -u  更新现有档案
>     -v  在标准输出中生成详细输出
>     -f  指定档案文件名
>     -m  包含指定清单文件中的清单信息
>     -n  创建新档案后执行 Pack200 规范化
>     -e  为绑定到可执行 jar 文件的独立应用程序
>         指定应用程序入口点
>     -0  仅存储; 不使用任何 ZIP 压缩
>     -M  不创建条目的清单文件
>     -i  为指定的 jar 文件生成索引信息
>     -C  更改为指定的目录并包含以下文件
> 如果任何文件为目录, 则对其进行递归处理。
> 清单文件名, 档案文件名和入口点名称的指定顺序
> 与 'm', 'f' 和 'e' 标记的指定顺序相同。
> 
> 示例 1: 将两个类文件归档到一个名为 classes.jar 的档案中:
>        jar cvf classes.jar Foo.class Bar.class
> 示例 2: 使用现有的清单文件 'mymanifest' 并
>            将 foo/ 目录中的所有文件归档到 'classes.jar' 中:
>        jar cvfm classes.jar mymanifest -C foo/ .
> ```

针对上面2点特性，spring boot的做法是：

1. 在Spring Boot 1.x有一定的局限性，但在spring boot2.0中利用嵌入式Tomcat API构建`TomcatWebServer` Bean，由spring 应用上下文将其引导（嵌入tomcat的各个组件【context、connector等】以及classloader的装载）。
2. 不考虑压缩模式，使用`spring-boot-loader`内建JAR协议`URLStreamHandle`来加载其内部的资源


由此得知，spring boot在嵌入式容器的设计或多或少是借鉴了它的实现。

#### Jetty

Google选择用Jetty取代Tomcat，有其必然性。归根到底是当时还没有spring boot，当时更看重Jetty的大小和灵活性（可插拔和可拓展行强，可以更大限度的进行定制）。



#### Undertow

x

#### Embedded Reactive Server

[I think you ~~like~~](https://docs.spring.io/spring-boot/docs/2.1.10.RELEASE/reference/html/boot-features-developing-web-applications.html#boot-features-reactive-server)

https://zhuanlan.zhihu.com/p/30813274