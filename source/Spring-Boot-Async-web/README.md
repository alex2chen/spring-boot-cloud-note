### 1.为什么需要异步模式
    异步其实是个老生常谈的话题，异步的本质在于请求处理线程不在阻塞，工作线程完成处理后勾起一个回调处理线程（回调处理线程和请求处理线程原因），由回调处理线程向浏览器反馈结果。
### 2.springMvc在4.x版本开始支持异步
    环境要求，Servlet版本是3.x以上，Spring MVC版本是4.x以上，建议使用最新，例如:
```xml
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.1.0</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>4.2.3.RELEASE</version>
    </dependency>
```
### 3.使用DefferedResult特性
    由于Spring MVC的良好封装，异步功能使用特别简单。在Controller中，传统的同步模式返回的ModelAndView，而异步模式则是返回DeferredResult<ModelAndView>
```java
    @GetMapping("/sync")
    public DeferredResult<String> sync() {
        DeferredResult<String> result = new DeferredResult<String>();
        LOGGER.info("start....");
        longTimeAsyncCallService.exec((ret) -> {
            LOGGER.info("sync complete...." + ret);
            result.setResult("OK");
        });
        return result;
    }
```
    结果：
    [nio-8080-exec-1] DemoController          : start....
    [nio-8080-exec-1] c.example.demo.LongTimeAsyncCallService  : 完成此任务需要等待:5秒.
    [pool-1-thread-1] c.example.demo.LongTimeAsyncCallService  : sync start.
    [pool-1-thread-1] DemoController          : sync complete....callback result 3
### 4.使用WebAsyncTask特性
    与上一个不同之处在于返回WebAsyncTask的话是不需要我们主动去调用Callback的，它仅仅是简单地把请求处理线程的任务转交给另一工作线程而已
```java
   @GetMapping("/sync2")
    public WebAsyncTask<String> syncTask() {
        LOGGER.info("start....");
        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(5000); //假设是一些长时间任务
                LOGGER.info("exec sync complete....");
                return "suc";
            }
        };
        return new WebAsyncTask(callable);
    }
```
    结果：
    [nio-8080-exec-4] DemoController          : start....
    [      my-task-2] DemoController          : exec sync complete....
### 5.超时处理
```java
    @GetMapping("/sync-timeout")
    public DeferredResult<String> syncTimeout() {
        DeferredResult<String> deferredResult = new DeferredResult<String>(2000L);
        LOGGER.info("start....timeout:2s");
        longTimeAsyncCallService.exec(new LongTermTaskCallback() {
            @Override
            public void callback(Object result) {
                LOGGER.info("exec sync complete...." + result);
                deferredResult.setResult("Ok");
            }
        });
        deferredResult.onTimeout(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("exec timeout complete....");
                deferredResult.setResult("timeout");
            }
        });
        return deferredResult;
    }
```
    DefferedResult结果：
        [nio-8080-exec-7] DemoController          : start....timeout:2s
        [nio-8080-exec-7] c.example.demo.LongTimeAsyncCallService  : 完成此任务需要等待:5秒.
        [nio-8080-exec-8] DemoController          : exec timeout complete....
        [pool-1-thread-1] c.example.demo.LongTimeAsyncCallService  : sync start.
        [pool-1-thread-1] DemoController          : exec sync complete....callback result 8
```java
    @GetMapping("/sync-timeout2")
    public WebAsyncTask<String> syncTaskTimeout() {
        LOGGER.info("start....timeout:2s");
        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(5000); //假设是一些长时间任务
                LOGGER.info("exec sync complete....");
                return "suc";
            }
        };
        WebAsyncTask asyncTask = new WebAsyncTask(2000, callable);
        asyncTask.onTimeout(new Callable<String>() {
            public String call() throws Exception {
                LOGGER.info("exec timeout complete....");
                return "timeout";
            }
        });
        return asyncTask;
    }
```
     WebAsyncTask结果：
        [io-8080-exec-10] DemoController          : start....timeout:2s
        [nio-8080-exec-1] DemoController          : exec timeout complete....
        [      my-task-3] DemoController          : exec sync complete....
### 6.异常处理
```java
@ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(Exception ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("result", ex.getMessage());
        return model;
    }
```
### Callable和Deferredresult区别
Callable和Deferredresult做的是同样的事情——释放容器线程，在另一个线程上异步运行长时间的任务。不同的是谁管理执行任务的线程:Callable执行线程完毕即返回；Deferredresult通过设置返回对象值（deferredResult.setResult(result));）返回，可以在任何地方控制返回。

springboot中基本配置： http://wiselyman.iteye.com/blog/2215852
DeferredResult的超时处理：https://blog.csdn.net/lxhjh/article/details/70240722