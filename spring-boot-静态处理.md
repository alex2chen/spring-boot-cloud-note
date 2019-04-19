springboot在处理web静态请求上和springmvc在使用是有些不同的，同时还增加了一些新的特性。

### 1.静态页面
	springboot项目只有src目录，没有webapp目录，会将静态访问(html/图片等)映射到其自动配置的静态目录。
	src/main/resources/static
	src/main/resources/public
	src/main/META-INF/static
	涉及默认配置修改
	spring.mvc.static-path-pattern=/**
	spring.resources.static-locations = classpath:/templates/,classpath:/static/,classpath:/public/
	很多时候上面的方式(springBoot使用WebMvcAutoConfiguration完成的)不会生效同时也比较局限，这时候需要手动编写代码来完成。
	但是如果你想完全控制Spring MVC，你可以在@Configuration注解的配置类上增加@EnableWebMvc，增加该注解以后WebMvcAutoConfiguration中配置就不会生效，你需要自己来配置需要的每一项。
	继承WebMvcConfigurerAdapter，然后重写方法：
```java
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/mystatic/**").addResourceLocations("classpath:/mystatic/");
	}
```
### 2.动态页面 
	动态页面需要先请求web服务器访问后台程序，然后再转向到页面。比如JSP，但spring boot建议不要使用它，默认集成Thymeleaf来做动态页面。
	首先在pom.xml中添加
```xml
	<dependency>  
	    <groupId>org.springframework.boot</groupId>  
	    <artifactId>spring-boot-starter-thymeleaf</artifactId>  
	</dependency>  
```
	再编写controller，
```java
	@Controller  
	public class TemplatesController {    

		@GetMapping("/templates")  
		String test(HttpServletRequest request) {  
			//逻辑处理  
			request.setAttribute("key", "hello world");  
			return "index";  
		}    
	}   
```
	最后，编写页面并保存到目录src/main/resources/templates
```html
	<html>  
	   <span th:text="${key}"></span>  
	</html> 
```
	当然如果要修改模式配置，可以通过
	spring.thymeleaf.prefix=classpath:/templates/
	spring.thymeleaf.suffix=.html
	# 所有的模板运行被devtools重新加载
	spring.thymeleaf.cache=false
### 3.webjars
	什么是webjars？在Web开发中，前端页面中用了越来越多的JS或CSS，如jQuery等等，平时我们是将这些Web资源拷贝到Java的目录下，这种通过人工方式拷贝可能会产生版本误差和错误，导致页面就无法正确展示。WebJars就是将这些Web前端资源打包成Jar包，然后借助Maven来做依赖项的管理。
	WebJars：http://www.webjars.org/
	如：jquery
	<dependency>
       <groupId>org.webjars</groupId>
       <artifactId>jquery</artifactId>
       <version>2.1.4</version>
	</dependency>
	文件存放结构为：
    META-INF/resources/webjars/jquery/2.1.4/jquery.js
    META-INF/resources/webjars/jquery/2.1.4/jquery.min.js
    META-INF/resources/webjars/jquery/2.1.4/jquery.min.map
    META-INF/resources/webjars/jquery/2.1.4/webjars-requirejs.js
	SpringBoot默认将 /webjars/** 映射到classpath:/META-INF/resources/webjars/
	
### 4.webjars-locator
	在实际的开发中，可能会遇到升级版本号的情况，如果我们有100多个页面，几乎每个页面上都有按上面引入jquery.js 那么我们要把版本号更换为3.0.0，一个一个替换显然不是最好的办法。其实该问题的本质是要应对版本号统一管理的需求。
	<dependency>
       <groupId>org.webjars</groupId>
       <artifactId>webjars-locator</artifactId>
    </dependency>
	增加一个WebJarsController，它会将webjars请求路径拦截，然后重新组装处理
```java
@Controller
public class WebJarController {
    private final WebJarAssetLocator assetLocator = new WebJarAssetLocator();

    @ResponseBody
    @RequestMapping("/webjarslocator/{webjar}/**")
    public ResponseEntity locateWebjarAsset(@PathVariable String webjar, HttpServletRequest request) {
        try {
            String mvcPrefix = "/webjarslocator/" + webjar + "/";
            String mvcPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
            String fullPath = assetLocator.getFullPath(webjar, mvcPath.substring(mvcPrefix.length()));
            return new ResponseEntity(new ClassPathResource(fullPath), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
```
	页面使用<script type="text/javascript" src="/webjarslocator/jquery/jquery.js"/>
### 5.静态资源版本管理
	当我们资源内容发生变化时，由于浏览器缓存用户本地的静态资源还是旧的资源，为了防止这种情况导致的问题，我们可能会手动在请求url的时候加个版本号或者其他方式。如<script type="text/javascript" src="/js/sample.js?v=1.0.1"></script>，但这样就好吗？
	存在很多文件都需要修改的问题，或者有的人会增加时间戳的方式，这样都是最不可取的，页面加载速度变慢，服务器增加了不必要的压力。
	而Spring在解决这种问题方面，提供了2种解决方式(针对freemarker为例)：
#### 5.1资源名-md5方式
	首先在application.properties中增加
	spring.resources.chain.strategy.content.enabled=true
	spring.resources.chain.strategy.content.paths=/**
	然后创建 ResourceUrlProviderController
```java
@ControllerAdvice
public class ControllerConfig {

    @Autowired
    ResourceUrlProvider resourceUrlProvider;

    @ModelAttribute("urls")
    public ResourceUrlProvider urls() {
        return this.resourceUrlProvider;
    }

}
```
	最后页面上调整为：
	<script type="text/javascript"src="${urls.getForLookupPath('/js/user.js') }"/>
	如果使用thymeleaf模板引擎呢？
	<script type="text/javascript"th:src="${urls.getForLookupPath('/js/user.js') }"/>
	最终，我们发现页面上是这样的
	<script type="text/javascript"src="/js/user-ef8d9e1da763788be348c78ea32a3c6d.js"/>
	原理：
	当请求的地址为md5方式时，会尝试url中的文件名中是否包含-，如果包含会去掉后面这部分，然后去映射的目录（如/static/）查找/js/common.js文件，如果能找到就返回。
#### 5.2资源版本号方式
	除了application.properties中有以下调整，其他的与上面基本上一致的
	spring.resources.chain.strategy.fixed.enabled=true
	spring.resources.chain.strategy.fixed.paths=/js/**,/v1.0.0/**
	spring.resources.chain.strategy.fixed.version=v1.0.0
	最终，页面上是这样的(页面上也是使用urls.getForLookupPath)
	<script type="text/javascript"src="/v1.0.0/js/demo.js"/>
	原理：
	 当请求的地址为版本号方式时，会在url中判断是否存在/v1.0.0 ，如果存在则先从URL中把/v1.0.0去掉，然后再去映射目录查找对应文件，找到就返回。
#### 5.3 小结
	1. 使用第三方的库时，建议使用webjars的方式，通过动态版本号（webjars-locator 的方式）来使用
	2. 使用自己存放在静态资源映射目录中的资源的时候，建议使用md5 资源文件名的方式来使用（项目开发中一些css、js文件会经常修改）。
	3. 项目素材文件建议放到 classpath:/static（或其他）目录中，打包在项目中，可以通过CMS维护的一些图片和资源。
	4. 注意使用md5时候，Spring是有缓存机制的，在服务不重启的情况下，你去变动修改这些文件名的md5值并不会改变，只有重启服务才会生效。如果需要每次都获取实际文件的md5值，需要重写相关类来实现，我们不建议这样做，因为一直去计算文件md5值是需要性能代价的。

	