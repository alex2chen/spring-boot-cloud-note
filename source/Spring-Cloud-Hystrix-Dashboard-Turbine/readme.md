## 监控单体应用
### Step1
	启动Spring-Cloud-Eureka-Service项目的集群
### Step2
	启动Spring-Cloud-Eureka-Client作为Server-Provider，访问 http://localhost:8082
### Step3
	启动Spring-Cloud-Hystrix-Circuit-Breaker ，访问 http://localhost:8083/hystrix.stream
### Step4
	启动Spring-Cloud-Hystrix-Dashboard ，访问 http://localhost:9002/hystrix

## 集群监控-Turbine
### Step1
	启动Spring-Cloud-Eureka-Service项目的集群
### Step2
	启动Spring-Cloud-Eureka-Client作为Server-Provider，访问 http://localhost:8082
### Step3
	启动Spring-Cloud-Hystrix-Circuit-Breaker 两个实例（两个实例的端口不一致，另一个指定-Dserver.port=8084），访问 http://localhost:8083/hystrix.stream
### Step4
	启动Spring-Cloud-Turbine，访问 http://localhost:9003/turbine.stream
### Step5
	启动Spring-Cloud-Hystrix-Dashboard ，访问 http://localhost:9002/hystrix	
	
## 集群监控-Turbine rabbit
### Step1
	启动Spring-Cloud-Eureka-Service项目的集群
### Step2
	启动Spring-Cloud-Eureka-Client作为Server-Provider，访问 http://localhost:8082
### Step3
	启动Spring-Cloud-Hystrix-Circuit-Breaker 两个实例（两个实例的端口不一致，另一个指定-Dserver.port=8084），访问 http://localhost:8083/hystrix.stream
### Step4
	启动Spring-Cloud-Turbine-Stream-rabbit，访问 http://localhost:9003/turbine.stream
### Step5
	启动Spring-Cloud-Hystrix-Dashboard ，访问 http://localhost:9002/hystrix	