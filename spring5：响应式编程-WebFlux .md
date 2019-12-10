> 响应式宣言
>
> We want systems that are Responsive, Resilient, Elastic and Message Driven. We call these Reactive Systems. -- The Reactive Manifesto

1. Responsive: 可响应的，要求系统尽可能做到在任何时候都能及时响应
2. Resilient: 可恢复的，要求系统即使出错了，也能保持可响应性
3. Elastic: 可伸缩的，要求系统在各种负载下都能保持可响应性
4. Message Driven: 消息驱动的，要求系统通过异步消息连接各个组件

Spring 5.0新引入的基于Reactive Streams的Spring WebFlux框架，它的组件体系是这样的：

 ![WebFlux](ext/spring-Reactive.jpg?raw=true)

### 1.1. Overview

https://docs.spring.io/spring/docs/5.1.11.RELEASE/spring-framework-reference/web-reactive.html#webflux

