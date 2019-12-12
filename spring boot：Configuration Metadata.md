在spring boot 或第三方的starter 项目中随处可见`additional-spring-configuration-metadata.json`、`spring-configuration-metadata.json`等元文件。这些元数据文件提供了配置属性的详细信息，旨在让开发人员在IDE编写`application.properties`或`application.yml`文件时提供智能提示。[更多可参与官网](https://docs.spring.io/spring-boot/docs/2.1.2.RELEASE/reference/html/configuration-metadata.html)

[TOC]

#### B.1 Metadata Format

配置元数据文件位于jar下面， `META-INF/spring-configuration-metadata.json`它们使用简单的JSON格式，其中的项目分类在“groups”或“properties”下，其他值提示分类在“hints”下，如下例所示：

```jso
{
	"groups": [
		{
			"name": "server",
			"type": "org.springframework.boot.autoconfigure.web.ServerProperties",
			"sourceType": "org.springframework.boot.autoconfigure.web.ServerProperties"
		},
		{
			"name": "spring.jpa.hibernate",
			"type": "org.springframework.boot.autoconfigure.orm.jpa.JpaProperties$Hibernate",
			"sourceType": "org.springframework.boot.autoconfigure.orm.jpa.JpaProperties",
			"sourceMethod": "getHibernate()"
		}
	],
	"properties": [
		{
			"name": "server.port",
			"type": "java.lang.Integer",
			"sourceType": "org.springframework.boot.autoconfigure.web.ServerProperties"
		},
		{
			"name": "server.address",
			"type": "java.net.InetAddress",
			"sourceType": "org.springframework.boot.autoconfigure.web.ServerProperties"
		}
	],
	"hints": [
		{
			"name": "spring.jpa.hibernate.ddl-auto",
			"values": [
				{
					"value": "none",
					"description": "Disable DDL handling."
				},
				{
					"value": "validate",
					"description": "Validate the schema, make no changes to the database."
				},
				{
					"value": "update",
					"description": "Update the schema if necessary."
				}
			]
		}
	]
}
```

用户可以为每个配置项“property”指定的值，例如，可以在`application.properties`中指定`server.port`和`server.address`，如下所示：

```properties
server.port=9090
server.address=127.0.0.1
```

“groups”是更高级别的项，它们本身并不指定值，而是为属性提供上下文分组。例如，`server.port`和`server.address`属性是“server”组的一部分。

<font color=#ff0000>并不要求每个“property”都有一个“group”。一些属性可能存在于他们自己的作用域中。</font>

最后，“hints”是用于帮助用户配置给定属性的枚举信息。例如，当开发人员配置`spring.jpa.hibernate.ddl-auto`时，工具可以提示为`none`, `validate`, `update`。

##### B.1.1 Group Attributes

| Name           | Type   | Purpose                                                      |
| -------------- | ------ | ------------------------------------------------------------ |
| `name`         | String | 组的全名。必填                                               |
| `type`         | String | 组的数据类型的类名. 例如，如果组基于用`@ConfigurationProperties`注解的类，则该属性将包含该类的完全限定名。如果它基于@Bean方法，那么它就是该方法的返回类型。如果类型未知，则可以省略该属性。 |
| `description`  | String | 可显示给用户的组的简短描述。如果没有可用的说明，可以省略。建议以简短的段落描述，第一行提供简明的摘要。说明中的最后一行应以句点结尾 (`.`). |
| `sourceType`   | String | 提供此组的源的类名. 例如，如果组基于用`@ConfigurationProperties`注解的`@Bean`方法，则此属性将包含包含该方法的`@Configuration`类的完全限定名。如果源类型未知，则可以省略该属性。 |
| `sourceMethod` | String | 提供此组的方法的全名（包括括号和参数类型）（例如，`@ConfigurationProperties` 注解的`@Bean`方法的名称）。如果源方法未知，则可以省略它。 |

##### B.1.2 Property Attributes

| Name           | Type        | Purpose                                                      |
| -------------- | ----------- | ------------------------------------------------------------ |
| `name`         | String      | 属性的全名。名称采用小写句点分隔形式（例如，`server.address`）。必填 |
| `type`         | String      | 属性数据类型（例如`java.lang.String`）的完整签名，但也是完整的泛型类型（例如`java.util.Map<java.util.String，acme.MyEnum>`）。 可以使用此属性指导用户输入值的类型。为了保持一致性，原语的类型通过使用其包装器对应项来指定（例如，`boolean`变为`java.lang.Boolean`）。请注意，该类可能是一个复杂类型，在绑定值时从字符串转换而来。如果类型未知，则可以省略。 |
| `description`  | String      | 同上个`description`                                          |
| `sourceType`   | String      | 提供此属性的源的类名。例如，如果属性来自用`@ConfigurationProperties`注释的类，则此属性将包含该类的完全限定名。如果源类型未知，则可以省略它。 |
| `defaultValue` | Object      | 默认值，在未指定属性时使用。如果属性的类型是数组，则它可以是值数组。如果默认值未知，则可以省略。 |
| `deprecation`  | Deprecation | 指定属性是否已弃用。如果该字段未被弃用，或者该信息未知，则可以省略该字段。 |

每个`properties`元素的`deprecation`属性中包含的JSON对象可以包含以下属性：

| Name          | Type   | Purpose                                                      |
| ------------- | ------ | ------------------------------------------------------------ |
| `level`       | String | 弃用级别, 可以是 `warning` (默认) or `error`. 当属性具有`warning` 弃用级别时, 它仍应在环境中绑定。 但是，当它具有`error`弃用级别时，该属性将不再受管理且不受绑定。 |
| `reason`      | String | 同上`description`                                            |
| `replacement` | String | 替换此已弃用属性的属性的全名。如果此属性没有替换项，则可以省略。 |

<font color=#ff0000>在Spring Boot 1.3之前，可以使用一个`deprecated`的boolean属性来代替`deprecation `元素。这仍然以`deprecated`的方式支持，不应再使用。如果没有可用的原因和替换，则应设置空的`deprecation`对象。</font>

通过将`@DeprecatedConfigurationProperty`注解添加到公开弃用属性的getter，也可以在代码中以声明方式指定弃用。例如，假设`app.acme.target`属性令人困惑，并被重命名为`app.acme.name`。下面的示例演示如何处理这种情况：

```java
@ConfigurationProperties("app.acme")
public class AcmeProperties {
	private String name;
	public String getName() { ... }
	public void setName(String name) { ... }
	@DeprecatedConfigurationProperty(replacement = "app.acme.name")
	@Deprecated
	public String getTarget() {
		return getName();
	}
	@Deprecated
	public void setTarget(String target) {
		setName(target);
	}
}
```

前面的代码确保过时的属性仍然有效（在幕后委托给name属性）。一旦`getTarget`和`setTarget`方法可以从公共API中删除，元数据中的自动弃用提示也会消失。如果要保留提示，则添加具有`error`弃用级别的手动元数据可以确保用户仍然了解该属性。在提供`replacement`时，这样做特别有用。

##### B.1.3 Hint Attributes

| Name        | Type            | Purpose                                                      |
| ----------- | --------------- | ------------------------------------------------------------ |
| `name`      | String          | 此提示所引用的属性的全名。名称采用小写的句点分隔形式 (如 `spring.mvc.servlet.path`). 如果属性是个 map (如 `system.contexts`), 则提示要么应用于map 中的keys(`system.context.keys`) 要么应用于 values(`system.context.values`) 。必填 |
| `values`    | ValueHint[]     | `ValueHint`对象定义的有效值列表（在下表中描述）              |
| `providers` | ValueProvider[] | 由`ValueProvider`对象定义的提供程序列表（在本文档后面描述）  |

每个提示元素的`values`属性中包含的JSON对象可以包含下表中描述的属性：

| Name          | Type   | Purpose                                                      |
| ------------- | ------ | ------------------------------------------------------------ |
| `value`       | Object | 提示引用的元素的有效值。如果属性的类型是数组，则它也可以是值数组。必填 |
| `description` | String | 同上`description`                                            |

每个`hint`元素的`providers`属性中包含的JSON对象可以包含下表中描述的属性：

| Name         | Type        | Purpose                                                      |
| ------------ | ----------- | ------------------------------------------------------------ |
| `name`       | String      | 用于为提示所引用的元素提供附加内容帮助的提供程序的名称       |
| `parameters` | JSON object | 提供程序支持的任何其他参数（有关详细信息，请查看提供程序的文档）。 |

##### B.1.4 Repeated Metadata Items

具有相同“property”和“group”名称的对象可以在元数据文件中多次出现。例如，可以将两个单独的类绑定到同一前缀，每个类都有可能重叠的属性名。虽然多次出现在元数据中的相同名称不应是常见的，但元数据的使用者应注意确保他们支持该名称。

#### B.2 提供手动 Hints

为了改善用户体验并进一步帮助用户配置给定属性，可以提供以下附加元数据：

- 描述属性的潜在值列表。
- 关联提供者，将定义良好的语义附加到属性，以便工具可以根据项目的上下文发现潜在值的列表

##### B.2.1 Value Hint

每个提示的`name`属性引用属性的`name `，比如前面`spring.jpa.hibernate.ddl-auto`的每个值也有相应的描述。

如果您的属性是Map类型，则可以同时为键和值提供提示（但不能为映射本身提供提示）。`.keys`和`.values`后缀必须分别引用键和值

```java
@ConfigurationProperties("sample")
public class SampleProperties {

	private Map<String,Integer> contexts;
	// getters and setters
}
```

本例中是sample1和sample2。为了为键提供额外的内容帮助，可以将以下JSON添加到模块的手动元数据中：

```json
{
	"hints": [
		{
			"name": "sample.contexts.keys",
			"values": [
				{
					"value": "sample1"
				},
				{
					"value": "sample2"
				}
			]
		}
	]
}
```

我们建议您对这两个值使用枚举。如果你的IDE支持它，这是目前为止最有效的自动完成方法。

##### B.2.2 Value Providers

Providers 是将语义附加到属性的强大方法。在本节中，我们将定义可用于自己提示的官方提供者。但是，您最喜欢的IDE可能实现了其中的一些，或者没有实现其中的任何一个。而且，它最终也可以提供自己的服务。由于这是一个新特性，IDE供应商必须跟上它的工作方式。

| Name                    | Description                                                  |
| ----------------------- | ------------------------------------------------------------ |
| `any`                   | 允许提供任何附加值                                           |
| `class-reference`       | 自动完成项目中可用的类。通常由`target`参数指定的基类约束。   |
| `handle-as`             | 处理属性，就像它是由强制`target`参数定义的类型定义的一样。   |
| `logger-name`           | 自动完成有效的记录器名称和[logger groups](https://docs.spring.io/spring-boot/docs/2.1.2.RELEASE/reference/html/boot-features-logging.html#boot-features-custom-log-groups)。通常，当前项目中可用的包和类名可以自动完成，也可以定义组。 |
| `spring-bean-reference` | 自动完成当前项目中可用的bean名称。通常由`target`参数指定的基类约束。 |
| `spring-profile-name`   | 自动完成项目中可用的profile名称。                            |

对于给定的属性，只能有一个provider 处于活动状态，但如果它们都可以以某种方式管理属性，则可以指定多个provider 。确保首先放置最强大的提供者，因为IDE必须使用JSON部分中它可以处理的第一个提供者。如果不支持给定属性的提供程序，则也不提供特殊的内容帮助

**Any**

特殊的任何provider 值允许提供任何附加值。如果支持，则应应用基于属性类型的常规值验证。

如果您有一个值列表，并且任何额外的值仍应视为有效，则通常使用此provider 。

以下示例为`system.state`提供了`on`和`off`作为自动完成值：

```jso
{"hints": [
	{
		"name": "system.state",
		"values": [
			{
				"value": "on"
			},
			{
				"value": "off"
			}
		],
		"providers": [
			{
				"name": "any"
			}
		]
	}
]}
```

**class-reference** 

class-reference provider自动完成项目中可用的类。此提供程序支持以下参数：

| Parameter  | Type               | Default value | Description                                                  |
| ---------- | ------------------ | ------------- | ------------------------------------------------------------ |
| `target`   | `String` (`Class`) | *none*        | 类的完全限定名，应可分配给所选值。通常用于筛选非候选类。请注意，此信息可以由类型本身通过公开具有适当上限的类来提供。 |
| `concrete` | `boolean`          | true          | 指定是否仅将具体类视为有效候选。                             |

```json
{"hints": [
	{
		"name": "server.servlet.jsp.class-name",
		"providers": [
			{
				"name": "class-reference",
				"parameters": {
					"target": "javax.servlet.http.HttpServlet"
				}
			}
		]
	}
]}
```

**handle-as**

handle-as provider允许您将属性的类型替换为更高级的类型。这通常在属性具有`java.lang.String`类型时发生，因为您不希望配置类依赖于不在类路径上的类。此提供程序支持以下参数

| Parameter    | Type               | Default value | Description                          |
| ------------ | ------------------ | ------------- | ------------------------------------ |
| **`target`** | `String` (`Class`) | *none*        | 要为属性考虑的类型的完全限定名，必填 |

可以使用以下类型：

- 任何`java.lang.Enum`: 列出属性的可能值(我们建议使用枚举类型定义属性，因为IDE不需要进一步提示就可以自动完成这些值)
- `java.nio.charset.Charset`: 支持自动完成字符集/编码值（如 UTF-8）
- ``java.util.Locale`:`: 自动完成区域设置（如 en_US）
- `org.springframework.util.MimeType`: 支持自动完成内容类型值（如 text/plain）
- `org.springframework.core.io.Resource`: 支持自动完成Spring的资源抽象以引用filesystem 或classpath上的文件(如 `classpath:/sample.properties`)

```json
{"hints": [
	{
		"name": "spring.liquibase.change-log",
		"providers": [
			{
				"name": "handle-as",
				"parameters": {
					"target": "org.springframework.core.io.Resource"
				}
			}
		]
	}
]}
```

**logger-name**

logger-name provider自动完成有效的logger名称和 [logger groups](https://docs.spring.io/spring-boot/docs/2.1.2.RELEASE/reference/html/boot-features-logging.html#boot-features-custom-log-groups)，通常，当前项目中可用的包和类名可以自动完成。如果组已启用（默认），并且配置中标识了自定义记录器组，则应提供该组的自动完成。特定框架可能还支持额外的magic logger名称。

| Parameter | Type      | Default value | Description            |
| --------- | --------- | ------------- | ---------------------- |
| `group`   | `boolean` | `true`        | 指定是否应考虑已知组。 |

以下元数据片段对应于标准`logging.level`属性。键是logger名称，值对应于标准日志级别或任何自定义级别。由于Spring Boot定义了一些现成的日志组，因此已经为它们添加了专用的值提示。

```json
{"hints": [
	{
		"name": "logging.level.keys",
		"values": [
			{
				"value": "root",
				"description": "Root logger used to assign the default logging level."
			},
			{
				"value": "sql",
				"description": "SQL logging group including Hibernate SQL logger."
			},
			{
				"value": "web",
				"description": "Web logging group including codecs."
			}
		],
		"providers": [
			{
				"name": "logger-name"
			}
		]
	},
	{
		"name": "logging.level.values",
		"values": [
			{
				"value": "trace"
			},
			{
				"value": "debug"
			},
			{
				"value": "info"
			},
			{
				"value": "warn"
			},
			{
				"value": "error"
			},
			{
				"value": "fatal"
			},
			{
				"value": "off"
			}

		],
		"providers": [
			{
				"name": "any"
			}
		]
	}
]}
```

**spring-bean-reference**

spring-bean-reference provider自动完成在当前项目的配置中定义的bean。此提供程序支持以下参数：

| Parameter | Type               | Default value | Description                                                  |
| --------- | ------------------ | ------------- | ------------------------------------------------------------ |
| `target`  | `String` (`Class`) | *none*        | 应该分配给候选对象的bean类的完全限定名。通常用于筛选非候选bean。 |

```json
{"hints": [
	{
		"name": "spring.jmx.server",
		"providers": [
			{
				"name": "spring-bean-reference",
				"parameters": {
					"target": "javax.management.MBeanServer"
				}
			}
		]
	}
]}
```

**spring-profile-name**

spring-profile-name provider自动完成在当前项目的配置中定义的Spring配置文件.

```json
{"hints": [
	{
		"name": "spring.profiles.active",
		"providers": [
			{
				"name": "spring-profile-name"
			}
		]
	}
]}
```

#### B.3 使用注解处理器生成你的元数据

通过使用`spring-boot-configuration-processor`jar，它在编译项目时触发，您可以从用`@ConfigurationProperties`注解的项中轻松地生成自己的配置元数据文件。

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-configuration-processor</artifactId>
	<optional>true</optional>
</dependency>
```

如果使用的是`additional-spring-configuration-metadata.json`文件，则`compileJava`任务应配置为依赖于`processResources`任务，如下例所示：

```java
compileJava.dependsOn(processResources)
```

此依赖关系确保在编译期间运行注解处理器时，附加元数据可用。处理器同时获取用`@ConfigurationProperties`注解的类和方法。配置类中字段值的Javadoc用于填充`description`属性。

只应将简单文本与`@ConfigurationProperties`字段Javadoc一起使用，因为在将它们添加到JSON之前不会对它们进行处理。

属性是通过标准getter和setter来发现的，它们对集合类型进行了特殊处理（即使只有getter存在，也会检测到）。注解处理器还支持使用`@Data`、`@Getter`和`@Setter`等 lombok注解。

##### B.3.1 Nested Properties

注解处理器自动将内部类视为嵌套属性。请考虑以下类别：

```java
@ConfigurationProperties(prefix="server")
public class ServerProperties {

	private String name;

	private Host host;

	// ... getter and setters

	public static class Host {

		private String ip;

		private int port;

		// ... getter and setters

	}

}
```

前面的示例生成`server.name`、`server.host.ip`和`server.host.port`属性的元数据信息。可以在字段上使用`@NestedConfigurationProperty`注解来指示应将常规（非内部）类视为嵌套类。

##### B.3.2 Adding Additional Metadata

Spring Boot的配置文件处理非常灵活，通常情况下，可能存在不绑定到`@ConfigurationProperties`的Bean。为了支持这种情况并让您提供自定义的“提示”，注解处理器会自动将`META-INF/additional-spring-configuration-metadata.json`中的项合并到主元数据文件中。

如果引用已自动检测到的属性，则将覆盖描述、默认值和弃用信息（如果指定）。如果当前模块中未标识手动属性声明，则将其添加为新属性。

`additional-spring-configuration-metadata.json`文件的格式与常规的`spring-configuration-metadata.json`完全相同。附加属性文件是可选的。如果没有任何其他属性，请不要添加文件。