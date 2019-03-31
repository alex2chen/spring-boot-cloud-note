要了解spring cloud，首先你得了解[spring boot](https://github.com/alex2chen/spring-boot-cloud-note/blob/master/readme.md)，如果你不知道可能就比较难以上手了。spring cloud 已成为一套完美微服务解决框架，它提供了一整套服务治理相关的东西，比如：服务注册/发现、断路器、负载均衡、接口网关、接口方式调用、分布式配置中心等。我相信只要往微服务，基本上spring cloud 是绕不开的话题。Dubbo采用的通信机制 RPC，spring cloud采用http的restful api。

### spring cloud 深入研究领域
- [Netflix Eureka](https://springcloud.cc/spring-cloud-dalston.html#spring-cloud-eureka-server "服务发现")
- [api gateway Zuul](https://springcloud.cc/spring-cloud-dalston.html#_router_and_filter_zuul)

### 推荐图书
首先，你要达到入门级的水平，要系统化的学习，这里推荐你看书《Spring Cloud微服务实战》。

### 常见注解
<table><tbody><tr><td>@EnableEurekaServer</td>
	<td>eureka-server</td>
</tr><tr><td>@EnableDiscoveryClient</td>
	<td>eureka-client</td>
</tr><tr><td>@RibbonClient</td>
	<td>Ribbon</td>
</tr><tr><td>@LoadBalanced</td>
	<td>Ribbon</td>
</tr><tr><td>@EnableFeignClients</td>
	<td>feign</td>
</tr><tr><td>@FeignClient</td>
	<td>feign</td>
</tr><tr><td>@EnableConfigServer</td>
	<td>spring-config</td>
</tr><tr><td>@RefreshScope&nbsp;</td>
	<td>spring-config</td>
</tr><tr><td>@EnableCircuitBreaker</td>
	<td>hystrix</td>
</tr><tr><td>@EnableHystrix</td>
	<td>hystrix（继承@EnableCircuitBreaker）</td>
</tr><tr><td>@HystrixCommand</td>
	<td>hystrix</td>
</tr><tr><td>@EnableTurbine</td>
	<td>turbine</td>
</tr><tr><td>@EnableTurbineStream</td>
	<td>turbine-amqp</td>
</tr><tr><td>@EnableHystrixDashboard</td>
	<td>hystrix-dashboard</td>
</tr><tr><td>@EnableZuulProxy</td>
	<td>zuul</td>
</tr><tr><td>@InboundChannelAdapter</td>
	<td>stream</td>
</tr><tr><td>@StreamListener&nbsp;</td>
	<td>stream</td>
</tr><tr><td>@Poller</td>
	<td>stream</td>
</tr><tr><td>@Transformer</td>
	<td>stream</td>
</tr><tr><td>@Output</td>
	<td>stream</td>
</tr><tr><td>@EnableBinding</td>
	<td>stream</td>
</tr><tr><td>@EnableZipkinServer</td>
	<td>sleuth-zipkin</td>
</tr><tr><td>@EnableZipkinStreamServer</td>
	<td>sleuth-zipkin-stream</td>
</tr></tbody></table>
	
### 为什么这个请求有效？http://COMPUTE-SERVICE/add?a=10&b=20   
	COMPUTE-SERVICE也不是域名，其实是ribbon的@LoadBalanced启作用的
	
### feign和Hystrix和Ribbon原理及源码
- [基于spring cloud-feign的异步支持](https://blog.csdn.net/alex_xfboy/article/details/81506076)
- [Spring Cloud Hystrix 源码系列：工作原理](https://blog.csdn.net/alex_xfboy/article/details/85069742)
- [Spring Cloud Hystrix 源码系列：HystrixCommandAspect 入口解析](https://blog.csdn.net/alex_xfboy/article/details/88720949)
- [Spring Cloud Hystrix 源码系列：隔离策略和请求缓存](https://blog.csdn.net/alex_xfboy/article/details/87990077)
- [Spring Cloud Hystrix 源码系列：HystrixRequestContext](https://blog.csdn.net/alex_xfboy/article/details/87989995)
- [Spring Cloud Hystrix 源码系列：Metrics 收集](https://blog.csdn.net/alex_xfboy/article/details/87989958)
- [Spring Cloud Hystrix 源码系列：熔断器](https://blog.csdn.net/alex_xfboy/article/details/87989008)
- [Spring Cloud Ribbon源码解析](https://blog.csdn.net/alex_xfboy/article/details/88166216)

最后，由于spring cloud组件使用的非常多，短时间内基本上很难达到高阶水平，后续会根据使用的情况专门分享。
