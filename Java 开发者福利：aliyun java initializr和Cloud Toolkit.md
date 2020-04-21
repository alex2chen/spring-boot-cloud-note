今天给大家分享2个国产优秀的开源工具，aliyun java initializr（spring脚手架）和Cloud Toolkit （idea plugin），工具是个好东西，比如PanDownload，可以让你在百度云盘上快速裸奔。

### 1.aliyun java initializr

近日阿里巴巴中间件发布不久的定制版`Spring Initializr`，熟悉`Spring Initializr`的同学知道，它提供的更多也是非常全的，更多是国外开源组件比较多，而`aliyun java initializr`基本上覆盖前者所有，同时有更多阿里开源组件的选择。

下面对比下可以感受下：

- https://start.spring.io/

![Spring Initializr](ext/Spring Initializr.jpg?raw=true)

- https://start.aliyun.com/

  ![start.aliyun](ext/start.aliyun.jpg?raw=true)



### Cloud Toolkit

Cloud Toolkit提供轻量级devops的idea plugin（Alibaba Cloud Toolkit），可以让你快速部署远程服务期（支持ssh、docker）、可以配置跳板机，让你在idea在本地一键部署、同时可以通过shell来管理远程主机。

1. 应用部署 | 实时日志
   - 部署到服务器，支持标准 SSH 协议，无需在一系列运维工具之间切换，只需在图形界面上选择目标服务器，即可实现应用快速部署
   - 部署到镜像仓库，针对阿里云镜像服务（ACR） 开发者，可以将登陆、拉取、推送、选择仓库等步骤交给插件自动化，实现一键完成所有操作。
   - 部署到 Kubernetes，针对阿里云 Kubernetes 开发者， 可以将本地代码和云端容器进行关联，实现自动化的镜像上传和部署。
   - 部署到 SAE（Serverless 应用引擎），针对阿里云 SAE 开发者，可以将应用快速部署到 SAE
2. 文件上传，一键将**本地**或者**远程 URL** 文件上传到服务器指定目录下去，无需在各种 FTP、SCP 工具之间频繁切换，
3. 远程 Terminal，比如文件上传完毕后，比如：文件解压缩、程序启动等可以支持**命令执行**，
4. 集成Arthas（ 阿里巴巴开源的一款应用诊断框架 ），即支持对于本地主机，也远程主机，首次会自动下载并安装，进入会话交互模式
5. 支持Apache Dubbo 脚手架，快速新建dubbo的一个Consumer 和 Provider。

 ![Cloud Toolkit1](ext/Cloud Toolkit1.jpg?raw=true)

![Cloud Toolkit2](ext/Cloud Toolkit2.jpg?raw=true)

其实对于一般小中型的公司而言，没有构建偏devops的效能平台，这些工具毫无疑问是极大的提高开发效能的，如果有特性需求，完全可以二开（二次开发）。

