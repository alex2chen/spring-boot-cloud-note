在有些情况下，我们还是要使用war，那就必须使用`maven-war-plugin`和`spring-boot-maven-plugin`，在我们使用了`spring-boot-dependencies`时会发现好像并不好使！

```xml
<plugin>
    <artifactId>maven-war-plugin</artifactId>
    <version>2.2</version>
</plugin>
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <version>2.1.3.RELEASE</version>
</plugin>
```

实际上在执行`java -jar`发现`spring-boot-maven-plugin`没有被运行，的确是的！

第一个问题，`maven-war-plugin`的这个版本，默认打包规则中必须存在web应用配置文件WEB-INF/web.xml，而`maven-war-plugin`从3.1.0就解决了这个问题。调整方式为：

```xml
<plugin>
    <artifactId>maven-war-plugin</artifactId>
    <version>3.2.2</version>
</plugin>
```

第二个问题，`spring-boot-maven-plugin`需要配置`repackage`否则不会添加spring boot引导类库（`spring-boot-loader`）。如果你项目直接依赖`spring-boot-starter-parent`，此问题就不存在了。

调整方式为：

```xml
<plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
      <executions>
          <execution>
              <id>repackage</id>
              <goals>
                  <goal>repackage</goal>
              </goals>
          </execution>
      </executions>
  </plugin>
```

