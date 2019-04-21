Consul是一款由HashiCorp公司开源的，用于服务治理的软件，Spring Cloud Consul对其进行了封装。Consul具有如下特点:<p/>
-	服务注册 - 自动注册和取消注册服务实例的网络位置
-	运行状况检查 - 检测服务实例何时启动并运行
-	分布式配置 - 确保所有服务实例使用相同的配置
### 运行模式
Consul agent有两种运行模式：Server和Client。这里的Server和Client只是Consul集群层面的区分，与搭建在Cluster之上 的应用服务无关。 以Server模式运行的Consul agent节点用于维护Consul集群的状态，官方建议每个Consul Cluster至少有3个或以上的运行在Server mode的Agent，Client节点不限。
### 安装Consul
Consul下载地址：https://www.consul.io/downloads.html，本文选择Linux 64bit 版本进行演示。<br/>
下载后解压，然后在解压目录下运行./consul命令：./consul agent -dev -client 192.168.140.215，启动后，默认的端口号为8500<br/>
-dev表示创建一个开发环境下的server节点，不该模式下会有任何持久化操作，即不会有任何数据写入到磁盘，所以这个模式适合用于开发过程，而不适用于生产环境。-client 192.168.140.215表示运行客户端使用ip地址192.168.140.215（本文Linux环境IP地址）去访问