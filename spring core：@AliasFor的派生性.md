spring对Annotation的派生性应用可谓炉火纯青，在[spring core：@Component的派生性](https://github.com/alex2chen/spring-boot-cloud-note/blob/master/spring%20core%EF%BC%9A%40Component%E7%9A%84%E6%B4%BE%E7%94%9F%E6%80%A7.md)讲过支持层次上派生性，而属性上派生的需求则借助了`@AliasFor`，它是从spring4.2中开始支持的。

`@AliasFor`注解用于声明注解元素的别名，应用于方法上（别忘了注解本质是接口）。Spring框架在内部使用大量的使用这个注解，例如，`@Bean`，`@ComponentScan`，`@Scope`等。

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AliasFor {
	/**
	 * 	 * 引用的注解别名..
	 */
	@AliasFor("attribute")
	String value() default "";
	/**
	 * 引用的注解别名.
	 * @see #value
	 */
	@AliasFor("value")
	String attribute() default "";
	/**层次结构的父注解
	 */
	Class<? extends Annotation> annotation() default Annotation.class;
}
```

**示例**1

可通过`AnnotatedElementUtils#getMergedAnnotationAttributes`来读取。下面演示没有派生性的情况：

```java
@Test
public void metaTest() throws IOException {
    AnnotationAttributes aa = AnnotatedElementUtils.getMergedAnnotationAttributes(Home.class, AccessRole.class);
    System.out.println(aa);//{value=super-user, module=gui, accessType=super-user}
}
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessRole {
    @AliasFor("accessType")
    String value() default "visitor";

    @AliasFor("value")
    String accessType() default "visitor";

    String module() default "gui";
}
@AccessRole("super-user")
//@AccessRole(value = "super-user",accessType = "super")//error
public class Home {
}
```

<font color=#ff0000>注：alias references的默认值必须一致，使用时指定值也必须一致，否则会抛出`AnnotationConfigurationException`</font>

下面演示有单层次及多层次派生性的情况：

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@AccessRole("admin")
public @interface AdminAccess {
    @AliasFor(annotation = AccessRole.class, attribute = "module")
    String value() default "service";
}
@AdminAccess
public class Home2 {
}
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@AdminAccess("supper")
public @interface SupperAccess {
    String value() default "service3";
    @AliasFor(annotation = AccessRole.class, attribute = "module")
    String module() default "service3";
}
@SupperAccess
public class Home3 {
}
@Test
public void metaTest() throws IOException {
    AnnotationAttributes aa = AnnotatedElementUtils.getMergedAnnotationAttributes(Home2.class, AdminAccess.class);
    System.out.println(aa);//{value=service}
    aa = AnnotatedElementUtils.getMergedAnnotationAttributes(Home2.class, AccessRole.class);
    System.out.println(aa);//{value=admin, accessType=admin, module=service}
    aa = AnnotatedElementUtils.getMergedAnnotationAttributes(Home3.class, AccessRole.class);
    System.out.println(aa);//{value=admin, module=service3, accessType=admin}
}
```

**原理**

`AnnotatedElementUtils`处理也不是特别复杂，很好理解

```java
public abstract class AnnotatedElementUtils {
	@Nullable
	public static AnnotationAttributes getMergedAnnotationAttributes(
			AnnotatedElement element, Class<? extends Annotation> annotationType) {

		AnnotationAttributes attributes = searchWithGetSemantics(element, annotationType, null,
				new MergedAnnotationAttributesProcessor());
		AnnotationUtils.postProcessAnnotationAttributes(element, attributes, false, false);
		return attributes;
	}
    //第一步：searchWithGetSemantics真正调用的方法，递归获取所有的注解
	@Nullable
	private static <T> T searchWithGetSemantics(AnnotatedElement element,
			Set<Class<? extends Annotation>> annotationTypes, @Nullable String annotationName,
			@Nullable Class<? extends Annotation> containerType, Processor<T> processor,
			Set<AnnotatedElement> visited, int metaDepth) {

		if (visited.add(element)) {
			try {
				// Start searching within locally declared annotations
				List<Annotation> declaredAnnotations = Arrays.asList(AnnotationUtils.getDeclaredAnnotations(element));
				T result = searchWithGetSemanticsInAnnotations(element, declaredAnnotations,
						annotationTypes, annotationName, containerType, processor, visited, metaDepth);
				if (result != null) {
					return result;
				}

				if (element instanceof Class) {  // otherwise getAnnotations doesn't return anything new
					Class<?> superclass = ((Class<?>) element).getSuperclass();
					if (superclass != null && superclass != Object.class) {
						List<Annotation> inheritedAnnotations = new LinkedList<>();
						for (Annotation annotation : element.getAnnotations()) {
							if (!declaredAnnotations.contains(annotation)) {
								inheritedAnnotations.add(annotation);
							}
						}
						// Continue searching within inherited annotations
						result = searchWithGetSemanticsInAnnotations(element, inheritedAnnotations,
								annotationTypes, annotationName, containerType, processor, visited, metaDepth);
						if (result != null) {
							return result;
						}
					}
				}
			}
			catch (Throwable ex) {
				AnnotationUtils.handleIntrospectionFailure(element, ex);
			}
		}

		return null;
	}
}
public abstract class AnnotationUtils {
    //第二步：获取@AliasFor的标记，处理其值
	static void postProcessAnnotationAttributes(@Nullable Object annotatedElement,
			@Nullable AnnotationAttributes attributes, boolean classValuesAsString, boolean nestedAnnotationsAsMap) {

		if (attributes == null) {
			return;
		}

		Class<? extends Annotation> annotationType = attributes.annotationType();

		// Track which attribute values have already been replaced so that we can short
		// circuit the search algorithms.
		Set<String> valuesAlreadyReplaced = new HashSet<>();

		if (!attributes.validated) {
			// Validate @AliasFor configuration
			Map<String, List<String>> aliasMap = getAttributeAliasMap(annotationType);
			aliasMap.forEach((attributeName, aliasedAttributeNames) -> {
				if (valuesAlreadyReplaced.contains(attributeName)) {
					return;
				}
				Object value = attributes.get(attributeName);
				boolean valuePresent = (value != null && !(value instanceof DefaultValueHolder));
				for (String aliasedAttributeName : aliasedAttributeNames) {
					if (valuesAlreadyReplaced.contains(aliasedAttributeName)) {
						continue;
					}
					Object aliasedValue = attributes.get(aliasedAttributeName);
					boolean aliasPresent = (aliasedValue != null && !(aliasedValue instanceof DefaultValueHolder));
					// Something to validate or replace with an alias?
					if (valuePresent || aliasPresent) {
						if (valuePresent && aliasPresent) {
							// Since annotation attributes can be arrays, we must use ObjectUtils.nullSafeEquals().
							if (!ObjectUtils.nullSafeEquals(value, aliasedValue)) {
								String elementAsString =
										(annotatedElement != null ? annotatedElement.toString() : "unknown element");
								throw new AnnotationConfigurationException(String.format(
										"In AnnotationAttributes for annotation [%s] declared on %s, " +
										"attribute '%s' and its alias '%s' are declared with values of [%s] and [%s], " +
										"but only one is permitted.", attributes.displayName, elementAsString,
										attributeName, aliasedAttributeName, ObjectUtils.nullSafeToString(value),
										ObjectUtils.nullSafeToString(aliasedValue)));
							}
						}
						else if (aliasPresent) {
							// Replace value with aliasedValue
							attributes.put(attributeName,
									adaptValue(annotatedElement, aliasedValue, classValuesAsString, nestedAnnotationsAsMap));
							valuesAlreadyReplaced.add(attributeName);
						}
						else {
							// Replace aliasedValue with value
							attributes.put(aliasedAttributeName,
									adaptValue(annotatedElement, value, classValuesAsString, nestedAnnotationsAsMap));
							valuesAlreadyReplaced.add(aliasedAttributeName);
						}
					}
				}
			});
			attributes.validated = true;
		}

		// Replace any remaining placeholders with actual default values
		for (Map.Entry<String, Object> attributeEntry : attributes.entrySet()) {
			String attributeName = attributeEntry.getKey();
			if (valuesAlreadyReplaced.contains(attributeName)) {
				continue;
			}
			Object value = attributeEntry.getValue();
			if (value instanceof DefaultValueHolder) {
				value = ((DefaultValueHolder) value).defaultValue;
				attributes.put(attributeName,
						adaptValue(annotatedElement, value, classValuesAsString, nestedAnnotationsAsMap));
			}
		}
	}
}
```