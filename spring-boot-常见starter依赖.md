#### 1.部分starters的依赖
```
spring-boot-starter-log4j2      ■ org.apache.logging.log4j:log4j-slf4j-impl
                                ■ org.apache.logging.log4j:log4j-api
                                ■ org.apache.logging.log4j:log4j-core	
                                ■ org.slf4j:jcl-over-slf4j	
                                ■ org.slf4j:jul-to-slf4j	
spring-boot-starter-logging     ■ ch.qos.logback:logback-classic
                                ■ org.slf4j:jcl-over-slf4j	
                                ■ org.slf4j:jul-to-slf4j	
                                ■ org.slf4j:log4j-over-slf4j	
spring-boot-starter-validation  ■ org.springframework.boot:spring-boot-starter
                                ■ org.apache.tomcat.embed:tomcat-embed-el	
                                ■org.hibernate:hibernate-validator					
spring-boot-starter-redis       ■ org.springframework.boot:spring-boot-starter
                                ■ org.springframework.data:spring-data-redis	
                                ■ redis.clients:jedis	
spring-boot-starter-security    ■ org.springframework.boot:spring-boot-starter
                                ■ org.springframework:spring-aop	
                                ■org.springframework.security:spring-security-config	
                                ■org.springframework.security:spring-security-web	
spring-boot-starter-test        ■ junit:junit
                                ■ org.mockito:mockito-core	
                                ■ org.hamcrest:hamcrest-core	
                                ■ org.hamcrest:hamcrest-library	
                                ■ org.springframework:spring-core(excludes commons-logging:commons-logging)	
                                ■ org.springframework:spring-test	
spring-boot-starter-web         ■ org.springframework.boot:spring-boot-starter
                                ■ org.springframework.boot:spring-boot-starter-tomcat	
                                ■ org.springframework.boot:spring-boot-starter-validation	
                                ■com.fasterxml.jackson.core:jackson-databind	
                                ■ org.springframework:spring-web	
                                ■ org.springframework:spring-webmvc	
spring-boot-starter-websocket   ■ org.springframework.boot:spring-boot-starter
                                ■ org.springframework.boot:spring-boot-starter-web	
                                ■ org.springframework:spring-messaging	
                                ■org.springframework:spring-websocket	
spring-boot-starter-ws          ■ org.springframework.boot:spring-boot-starter
                                ■ org.springframework.boot:spring-boot-starter-web 	
                                ■ org.springframework:spring-jms	
                                ■ org.springframework:spring-oxm	
                                ■ org.springframework.ws:spring-ws-core	
                                ■ org.springframework.ws:spring-ws-support						
spring-boot-starter-velocity    ■ org.springframework.boot:spring-boot-starter
                                ■ org.springframework.boot:spring-boot-starter-web	
                                ■ commons-beanutils:commons-beanutils	
                                ■ commons-collections:commons-collections	
                                ■ commons-digester:commons-digester	
                                ■ org.apache.velocity:velocity	
                                ■ org.apache.velocity:velocity-tools	
                                ■ org.springframework:spring-context-support                                
spring-boot-starter-thymeleaf   ■ org.springframework.boot:spring-boot-starter
                                ■ org.springframework.boot:spring-boot-starter-web	
                                ■ org.thymeleaf:thymeleaf-spring4	
                                ■nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect 
spring-boot-starter-mustache    ■ org.springframework.boot:spring-boot-starter
                                ■ org.springframework.boot:spring-boot-starter-web	
                                ■ com.samskivert:jmustache                                	
spring-boot-starter-tomcat      ■ org.apache.tomcat.embed:tomcat-embed-core
                                ■ org.apache.tomcat.embed:tomcat-embed-el	
                                ■org.apache.tomcat.embed:tomcat-embed-logging-juli	
                                ■org.apache.tomcat.embed:tomcat-embed-websocket	
spring-boot-starter-mail        ■ org.springframework.boot:spring-boot-starter
                                ■ org.springframework:spring-context
                                ■ org.springframework:spring-context-support
                                ■ com.sun.mail:javax.mail
spring-boot-starter-mobile      ■ org.springframework.boot:spring-boot-starter
                                ■ org.springframework.boot:spring-boot-starter-web
                                ■ org.springframework.mobile:spring-mobile-device
spring-boot-starter-remote-shell  ■ org.springframework.boot:spring-boot-starter
                                  ■ org.springframework.boot:spring-boot-starter-actuator
                                  ■ org.crashub:crash.cli
                                  ■ org.crashub:crash.connectors.ssh (excludes org.codehaus.groovy:groovy-all)
                                  ■ org.crashub:crash.connectors.telnet (excludes javax.servlet:servlet-api, log4j :log4j, commons-logging:commons-logging)
                                  ■ org.crashub:crash.embed.spring(excludes org.springframework:spring-web, org.codehaus.groovy:groovy-all)
                                  ■ org.crashub:crash.plugins.cron (excludes org.codehaus.groovy:groovy-all)
                                  ■ org.crashub:crash.plugins.mail (excludes org.codehaus.groovy:groovy-all)
                                  ■ org.crashub:crash.shell (excludes org.codehaus.groovy:groovy-all)
                                  ■ org.codehaus.groovy:groovy
spring-boot-starter-social-facebook ■ org.springframework.boot:spring-boot-starter
                                  	■ org.springframework.boot:spring-boot-starter-web
                                  	■ org.springframework.social:spring-social-config
                                  	■ org.springframework.social:spring-social-core
                                  	■ org.springframework.social:spring-social-web
                                  	■org.springframework.social:spring-social-facebook
spring-boot-starter-social-linkedin ■org.springframework.boot:spring-boot-starter
                                  	■ org.springframework.boot:spring-boot-starter-web
                                  	■ org.springframework.social:spring-social-config
                                  	■ org.springframework.social:spring-social-core
                                  	■ org.springframework.social:spring-social-web
                                  	■ org.springframework.social:spring-social-linkedin
spring-boot-starter-social-twitter  ■ org.springframework.boot:spring-boot-starter
                                  	■ org.springframework.boot:spring-boot-starter-web
                                  	■ org.springframework.social:spring-social-config
                                  	■ org.springframework.social:spring-social-core
                                  	■org.springframework.social:spring-social-web
                                  	■ org.springframework.social:spring-social-twitter	
```										
#### 2.常见jar迁移  
	druid -> 			druid-spring-boot-starter
	mybatis-spring ->   mybatis-spring-boot-starter:MybatisAutoConfiguration(自动注入@Mapper)
	jedis ->			spring-boot-starter-data-redis
	com.github.pagehelper:pagehelper
	spring-boot-starter-dubbo
	spring-boot-starter-swagger
	.....