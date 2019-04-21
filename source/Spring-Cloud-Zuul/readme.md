演示pring-Cloud-Zuul-Router的使用
```html
zuul:
  routes:
    api-a:
      path: /api-a/**
      url: http://localhost:8082
    api-b:
      path: /api-b/**
      serviceId: server-provider
    api-c:
      path: /api-c/**
      serviceId: server-consumer
    api-d:
      path: /api-c/user/1
      serviceId: lol
    api-e:
      path: /api-e/**
      url: forward:/test
```
### Step1
    启动Spring-Cloud-Eureka-Service项目的集群
### Step2
    启动Spring-Cloud-Eureka-Client作为Server-Provider，http://localhost:8082
### Step3
	启动Spring-Cloud-Feign作为Server-Consumer，访问 http://localhost:8083
###	Step4
	启用Spring-Cloud-Zuul作为网关，访问 http://localhost:9095
	
	