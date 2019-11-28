spring在java语言中非常成功，成功的关键在于作者发起人：Juergen Hoeller和Sam Brannen对J2EE规范非常熟悉，若按他们的视角来看，spring就是java领域的开发规范，很显然已经成为了超越J2EE事实上的标准。关于接口`org.springframework.core.Ordered`和注解`org.springframework.core.annotation.Order` 的使用在spring随处可见，特别是委托链及拦截器等排序功能中它发挥着非常关键的作用。

从`RestTemplate`作为切入点（不要问我为何从这开始，个人觉得这个工具类简单）看它用法，设置`ClientHttpRequestInterceptor`（请求拦截器）时进行排序：

```java
public class RestTemplate extends InterceptingHttpAccessor implements RestOperations {
}
public abstract class InterceptingHttpAccessor extends HttpAccessor {
	 /**
	 * Set the request interceptors that this accessor should use.
	 * <p>The interceptors will get sorted according to their order
	 */
	public void setInterceptors(List<ClientHttpRequestInterceptor> interceptors) {
		// Take getInterceptors() List as-is when passed in here
		if (this.interceptors != interceptors) {
			this.interceptors.clear();
			this.interceptors.addAll(interceptors);
			AnnotationAwareOrderComparator.sort(this.interceptors);
		}
	}
}
```

另外的用法就是：

```java
	private static <E> Set<E> asUnmodifiableOrderedSet(Collection<E> elements) {
		List<E> list = new ArrayList<>(elements);
		list.sort(AnnotationAwareOrderComparator.INSTANCE);
		return new LinkedHashSet<>(list);
	}
```

饮水思源，这恰恰是我们要具备的基本功。

其实`Priority`的定义及适用范围`Target`很模糊，感觉讲的都是白话（废话），但它提到了一个使用场景<u>For example, the Interceptors</u>，对Interceptors可以用。

而spring的`Order`则精准些，为了不过度解析特殊用法（在Spring 4.0、4.1中有增强），它适用于TYPE（类、注解、接口、枚举）、方法、成员变量。

而spring的`Ordered`的意义在于接口大于注解（如果一个类被标记了多个@Order并且还是先实现了`Ordered`则取接口），除此之外基本用法都与`Order`一致的。

```java
//源自 package javax.annotation;
/**
 * The <code>Priority</code> annotation can be applied to classes 
 * or parameters to indicate in what order they should be used.  
 * The effect of using the <code>Priority</code> annotation in
 * any particular instance is defined by other specifications that 
 * define the use of a specific class.
 * <p>
 * For example, the Interceptors specification defines the use of
 * priorities on interceptors to control the order in which
 * interceptors are called.</p>
 * <p>
 * Priority values should generally be non-negative, with negative values
 * reserved for special meanings such as "undefined" or "not specified".
 * A specification that defines use of the <code>Priority</code> annotation may define
 * the range of allowed priorities and any priority values with special
 * meaning.</p>
 *
 * @since Common Annotations 1.2
 */
@Target({TYPE,PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface Priority {
    /**
     * The priority value.
     */
    int value();
}
//源自package org.springframework.core.annotation;
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Documented
public @interface Order {
	int value() default Ordered.LOWEST_PRECEDENCE;
}
//源自package org.springframework.core;
public interface Ordered {
	int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;
	int LOWEST_PRECEDENCE = Integer.MAX_VALUE;
	/**
	 * Get the order value of this object.
	 * <p>Higher values are interpreted as lower priority. As a consequence,
	 * the object with the lowest value has the highest priority (somewhat
	 * analogous to Servlet {@code load-on-startup} values)
	 */
	int getOrder();
}
```

`AnnotationAwareOrderComparator`则是一个Comparator的工具类，它最重要的就是方法就是`findOrder`，它的优先级是：`Ordered`>`Priority`>`Order`, `Priority`可以标记对象为Class类型，`Order`则通杀。而`OrderUtils`则会缓存被排序的类型。

```java
public class AnnotationAwareOrderComparator extends OrderComparator {
    protected Integer findOrder(Object obj) {
		// Check for regular Ordered interface
		Integer order = super.findOrder(obj);
		if (order != null) {
			return order;
		}

		// Check for @Order and @Priority on various kinds of elements
		if (obj instanceof Class) {
			return OrderUtils.getOrder((Class<?>) obj);
		}
		else if (obj instanceof Method) {
			Order ann = AnnotationUtils.findAnnotation((Method) obj, Order.class);
			if (ann != null) {
				return ann.value();
			}
		}
		else if (obj instanceof AnnotatedElement) {
			Order ann = AnnotationUtils.getAnnotation((AnnotatedElement) obj, Order.class);
			if (ann != null) {
				return ann.value();
			}
		}
		else {
			order = OrderUtils.getOrder(obj.getClass());
			if (order == null && obj instanceof DecoratingProxy) {
				order = OrderUtils.getOrder(((DecoratingProxy) obj).getDecoratedClass());
			}
		}

		return order;
	}
    //工具类方法
    public static void sort(List<?> list) {
		if (list.size() > 1) {
			list.sort(INSTANCE);
		}
	}
    public static void sort(Object[] array) {
		if (array.length > 1) {
			Arrays.sort(array, INSTANCE);
		}
	}
    public static void sortIfNecessary(Object value) {
		if (value instanceof Object[]) {
			sort((Object[]) value);
		}
		else if (value instanceof List) {
			sort((List<?>) value);
		}
	}
} 
public abstract class OrderUtils {
	/** Cache for @Order value (or NOT_ANNOTATED marker) per Class. */
	private static final Map<Class<?>, Object> orderCache = new ConcurrentReferenceHashMap<>(64);

	/** Cache for @Priority value (or NOT_ANNOTATED marker) per Class. */
	private static final Map<Class<?>, Object> priorityCache = new ConcurrentReferenceHashMap<>();
	@Nullable
	public static Integer getOrder(Class<?> type) {
		Object cached = orderCache.get(type);
		if (cached != null) {
			return (cached instanceof Integer ? (Integer) cached : null);
		}
		Order order = AnnotationUtils.findAnnotation(type, Order.class);
		Integer result;
		if (order != null) {
			result = order.value();
		}
		else {
			result = getPriority(type);
		}
		orderCache.put(type, (result != null ? result : NOT_ANNOTATED));
		return result;
	}
	@Nullable
	public static Integer getPriority(Class<?> type) {
		if (priorityAnnotationType == null) {
			return null;
		}
		Object cached = priorityCache.get(type);
		if (cached != null) {
			return (cached instanceof Integer ? (Integer) cached : null);
		}
		Annotation priority = AnnotationUtils.findAnnotation(type, priorityAnnotationType);
		Integer result = null;
		if (priority != null) {
			result = (Integer) AnnotationUtils.getValue(priority);
		}
		priorityCache.put(type, (result != null ? result : NOT_ANNOTATED));
		return result;
	}
}    
```

至此，`Priority`和`Ordered`和`Order`已经将清楚了，非常简单规范的工具类，你完全可以信任并大量使用。



