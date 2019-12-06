​	Spring Boot有很多功能特性值得借鉴和学习，很多玩Spring Boot的人知道不需要安装Tomcat很方便，其实并没有发现Maven也是不需要提前安装。它这样做的好处在于解决了开发环境maven版本不一致导致的各种问题，spring boot中集成了maven-wrapper的确比较务实。

[TOC]

### 它是什么

相信大家都用到spring的脚手架：https://start.spring.io/ 来生成Spring Boot项目，而项目的根目录中会多几个文件：

> cmd > C:\Users\alex\Desktop\demo> tree /f
> │  mvnw            //linux-shell
> │  mvnw.cmd        //window-cmd
> │  pom.xml
> ├─.mvn
> │  └─wrapper
> │          maven-wrapper.jar
> │          maven-wrapper.properties
> │          MavenWrapperDownloader.java
> ├─src

maven-wrapper解决了2个问题：

1. 可以为某个Java工程指定某个特定Maven版本，避免因为版本差异引起的诡异错误，这样就统一该项目的开发环境；
2. 不再需要提前安装Maven，简化了开发环境的配置；

### 玩法及原理

#### 1.当前项目(spring boot)

在项目目录下执行mvnw clean，其实就是将之前你熟悉的mvn替换为mvnw命令即可，一点也不复杂。

mvnw第一次运行会检测$USER_HOME/.m2/wrapper/dists 目录下是否有maven-wrapper.properties中指定的Maven版本，如果没有就自动下载。

> 此时你会问下载后的maven会在哪里？
>
> 一般会在${user.home}\.m2\wrapper\dists目录，我的机器是在：C:\Users\alex\.m2\wrapper\dists\apache-maven-3.6.2-bin\795eh28tki48bv3l67maojf0ra

> 如何调整版本呢？
>
> 具体可参与maven-wrapper.properties中配置：
> distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.6.2/apache-maven-3.6.2-bin.zip
> wrapperUrl=https://repo.maven.apache.org/maven2/io/takari/maven-wrapper/0.5.5/maven-wrapper-0.5.5.jar

原理：mvnw.cmd逻辑也不复杂，先将maven-wrapper.jar添加到classpath，再运行>?<font color=86ca5e>MavenWrapperDownloader#main</font>。

```java
public class MavenWrapperDownloader {
    private static final String WRAPPER_VERSION = "0.5.5";
    /**
     * Default URL to download the maven-wrapper.jar from, if no 'downloadUrl' is provided.
     */
    private static final String DEFAULT_DOWNLOAD_URL = "https://repo.maven.apache.org/maven2/io/takari/maven-wrapper/"
        + WRAPPER_VERSION + "/maven-wrapper-" + WRAPPER_VERSION + ".jar";
 
    /**
     * Path to the maven-wrapper.properties file, which might contain a downloadUrl property to
     * use instead of the default one.
     */
    private static final String MAVEN_WRAPPER_PROPERTIES_PATH =
            ".mvn/wrapper/maven-wrapper.properties";
 
    /**
     * Path where the maven-wrapper.jar will be saved to.
     */
    private static final String MAVEN_WRAPPER_JAR_PATH =
            ".mvn/wrapper/maven-wrapper.jar";
 
    /**
     * Name of the property which should be used to override the default download url for the wrapper.
     */
    private static final String PROPERTY_NAME_WRAPPER_URL = "wrapperUrl";
 
    public static void main(String args[]) {
        System.out.println("- Downloader started");
        File baseDirectory = new File(args[0]);
        System.out.println("- Using base directory: " + baseDirectory.getAbsolutePath());
 
        // If the maven-wrapper.properties exists, read it and check if it contains a custom
        // wrapperUrl parameter.
        File mavenWrapperPropertyFile = new File(baseDirectory, MAVEN_WRAPPER_PROPERTIES_PATH);
        String url = DEFAULT_DOWNLOAD_URL;
        if(mavenWrapperPropertyFile.exists()) {
            FileInputStream mavenWrapperPropertyFileInputStream = null;
            try {
                mavenWrapperPropertyFileInputStream = new FileInputStream(mavenWrapperPropertyFile);
                Properties mavenWrapperProperties = new Properties();
                mavenWrapperProperties.load(mavenWrapperPropertyFileInputStream);
                url = mavenWrapperProperties.getProperty(PROPERTY_NAME_WRAPPER_URL, url);
            } catch (IOException e) {
                System.out.println("- ERROR loading '" + MAVEN_WRAPPER_PROPERTIES_PATH + "'");
            } finally {
                try {
                    if(mavenWrapperPropertyFileInputStream != null) {
                        mavenWrapperPropertyFileInputStream.close();
                    }
                } catch (IOException e) {
                    // Ignore ...
                }
            }
        }
        System.out.println("- Downloading from: " + url);
 
        File outputFile = new File(baseDirectory.getAbsolutePath(), MAVEN_WRAPPER_JAR_PATH);
        if(!outputFile.getParentFile().exists()) {
            if(!outputFile.getParentFile().mkdirs()) {
                System.out.println(
                        "- ERROR creating output directory '" + outputFile.getParentFile().getAbsolutePath() + "'");
            }
        }
        System.out.println("- Downloading to: " + outputFile.getAbsolutePath());
        try {
            downloadFileFromURL(url, outputFile);
            System.out.println("Done");
            System.exit(0);
        } catch (Throwable e) {
            System.out.println("- Error downloading");
            e.printStackTrace();
            System.exit(1);
        }
    }
 
    private static void downloadFileFromURL(String urlString, File destination) throws Exception {
        if (System.getenv("MVNW_USERNAME") != null && System.getenv("MVNW_PASSWORD") != null) {
            String username = System.getenv("MVNW_USERNAME");
            char[] password = System.getenv("MVNW_PASSWORD").toCharArray();
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        }
        URL website = new URL(urlString);
        ReadableByteChannel rbc;
        rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(destination);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
 
}
```

#### 2.全局命令

还可以mvn -N io.takari:maven:wrapper -Dmaven=3.3.3表示我们期望使用的Maven的版本为3.3.3，将mvnw提升为全局命令，个人感觉意义不大，它最大的贡献在于保留及坚持个性化。

#### 3.idea插件maven-wrapper-support

它需要结合当前项目（找maven-wrapper.properties配置）来使用。

maven-wrapper-support的逻辑也不复杂：会监测项目下的.mvn/wrapper/maven-wrapper.properties中的distributionUrl属性值，且自动下载maven版本到用户目录.m2/wrapper目录中，并且改变setting->build->build Tools ->maven-> maven home directory的值，但执行的命令是原生mvn的命令，而不是项目中下的mvnw命令。

![maven-wrapper-support](ext/maven-wrapper-support.png?raw=true)

原理：具体验证可查看plugin安装目录一般为：idea.config.path=${user.home}/.IntelliJIdea/config，比如我的：C:\Users\alex\.IntelliJIdea2018.3\config\plugins\maven-wrapper-support\lib中的maven-wrapper-support-0.5.1.jar代码<font color=86ca5e>MavenWrapperProjectComponent.class</font>。

```java
public class MavenWrapperProjectComponent extends AbstractProjectComponent {
    private VirtualFile wrapperSettings;
    private Logger log = Logger.getInstance(this.getClass());
 
    public MavenWrapperProjectComponent(Project project) {
        super(project);
    }
 
    private void applyWrapper() {
        if (this.wrapperSettings != null) {
            StringBuilder output = new StringBuilder();
            WrapperExecutor wrapperExecutor = WrapperExecutor.forWrapperPropertiesFile(new File(this.wrapperSettings.getPath()), output);
            File mavenUserHome = new File(System.getProperty("user.home") + "/.m2");
            Installer installer = new Installer(new DefaultDownloader("mvnw", "0.4.0"), new PathAssembler(mavenUserHome));
 
            try {
                File mavenHome = installer.createDist(wrapperExecutor.getConfiguration());
                this.changeMavenHomeTo(mavenHome.getAbsolutePath(), "maven wrapper defined in " + this.wrapperSettings.getPath());
            } catch (Exception var6) {
                this.log.error(var6);
            }
 
        }
    }
 
    private void changeMavenHomeTo(String mavenPath, String message) {
        MavenGeneralSettings generalSettings = MavenProjectsManager.getInstance(this.myProject).getGeneralSettings();
        if (generalSettings != null) {
            String oldMavenHome = generalSettings.getMavenHome();
            if (!mavenPath.equals(oldMavenHome)) {
                generalSettings.setMavenHome(mavenPath);
                this.log.info("Maven changed to " + message);
                Bus.notify(new Notification("maven-wrapper", "Maven changed", "Maven changed to " + message, NotificationType.INFORMATION));
            }
        }
    }
 
    public void projectOpened() {
        VirtualFileManager.getInstance().addVirtualFileListener(new com.blackbuild.intellij.wavenwrappersupport.MavenWrapperProjectComponent.ChangeListener(this));
        this.wrapperSettings = this.myProject.getBaseDir().findFileByRelativePath(".mvn/wrapper/maven-wrapper.properties");
        this.applyWrapper();
    }
 
    @NotNull
    public String getComponentName() {
        String var10000 = this.getClass().getName();
        if (var10000 == null) {
            $$$reportNull$$$0(0);
        }
 
        return var10000;
    }
}
```

**注意事项**

***1.distributionUrl下载会比较慢***

建议替换为：http://www-us.apache.org/dist/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.zip

***2.mvnw.bat中执行老版本的maven可能会报错："Error: M2_HOME is set to an invalid directory"***

Maven早期版本不叫mvn.cmd，而是叫mvn.bat，找到代码替换掉即可