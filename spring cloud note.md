要了解spring cloud，首先你得了解spring boot，如果你不知道可能就比较难以上手了。spring cloud 已成为一套完美微服务解决框架，它提供了一整套服务治理相关的东西，比如：服务注册/发现、断路器、负载均衡、接口网关、接口方式调用、分布式配置中心等。我相信只要往微服务，基本上spring cloud 是绕不开的话题。Dubbo采用的通信机制 RPC，spring cloud采用http的restful api。

### spring cloud 深入研究领域
- [api gateway Zuul](https://springcloud.cc/spring-cloud-dalston.html#_router_and_filter_zuul)
- [Spring Cloud Contract](https://springcloud.cc/spring-cloud-dalston.html#_spring_cloud_contract)

### 推荐图书
首先，你要达到入门级的水平，要系统化的学习，这里推荐你看书《Spring Cloud微服务实战》。

### 常见注解
<table border="1" cellpadding="1" cellspacing="1"><tbody><tr><td style="width:219px;">@EnableEurekaServer</td>
	<td style="width:630px;">eureka-server</td>
</tr><tr><td style="width:219px;">@EnableDiscoveryClient</td>
	<td style="width:630px;">eureka-client</td>
</tr><tr><td style="width:219px;">@RibbonClient</td>
	<td style="width:630px;">Ribbon</td>
</tr><tr><td style="width:219px;">@LoadBalanced</td>
	<td style="width:630px;">Ribbon</td>
</tr><tr><td style="width:219px;">@EnableFeignClients</td>
	<td style="width:630px;">feign</td>
</tr><tr><td style="width:219px;">@FeignClient</td>
	<td style="width:630px;">feign</td>
</tr><tr><td style="width:219px;">@EnableConfigServer</td>
	<td style="width:630px;">spring-config</td>
</tr><tr><td style="width:219px;">@RefreshScope&nbsp;</td>
	<td style="width:630px;">spring-config</td>
</tr><tr><td style="width:219px;">@EnableCircuitBreaker</td>
	<td style="width:630px;">hystrix</td>
</tr><tr><td style="width:219px;">@EnableHystrix</td>
	<td style="width:630px;">hystrix（继承@EnableCircuitBreaker）</td>
</tr><tr><td style="width:219px;">@HystrixCommand</td>
	<td style="width:630px;">hystrix</td>
</tr><tr><td style="width:219px;">@EnableTurbine</td>
	<td style="width:630px;">turbine</td>
</tr><tr><td style="width:219px;">@EnableTurbineStream</td>
	<td style="width:630px;">turbine-amqp</td>
</tr><tr><td style="width:219px;">@EnableHystrixDashboard</td>
	<td style="width:630px;">hystrix-dashboard</td>
</tr><tr><td style="width:219px;">@EnableZuulProxy</td>
	<td style="width:630px;">zuul</td>
</tr><tr><td style="width:219px;">@InboundChannelAdapter</td>
	<td style="width:630px;">stream</td>
</tr><tr><td style="width:219px;">@StreamListener&nbsp;</td>
	<td style="width:630px;">stream</td>
</tr><tr><td style="width:219px;">@Poller</td>
	<td style="width:630px;">stream</td>
</tr><tr><td style="width:219px;">@Transformer</td>
	<td style="width:630px;">stream</td>
</tr><tr><td style="width:219px;">@Output</td>
	<td style="width:630px;">stream</td>
</tr><tr><td style="width:219px;">@EnableBinding</td>
	<td style="width:630px;">stream</td>
</tr><tr><td style="width:219px;">@EnableZipkinServer</td>
	<td style="width:630px;">sleuth-zipkin</td>
</tr><tr><td style="width:219px;">@EnableZipkinStreamServer</td>
	<td style="width:630px;">sleuth-zipkin-stream</td>
</tr></tbody></table>
#### 常用对象
	RestTemplate
#### 为什么这个请求有效？http://COMPUTE-SERVICE/add?a=10&b=20   
	其实是ribbon的@LoadBalanced启作用的

最后，由于spring cloud组件使用的非常多，短时间内基本上很难达到高阶水平，后续会根据使用的情况专门分享。
