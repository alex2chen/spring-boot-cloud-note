C:\Windows\System32\drivers\etc\hosts中添加
127.0.0.1 peer1
127.0.0.1 peer2
然后准备两个启动项
java -jar Spring-Cloud-Eureka-Service-0.0.1-SNAPSHOT.jar --spring.profiles.active=server1
java -jar Spring-Cloud-Eureka-Service-0.0.1-SNAPSHOT.jar --spring.profiles.active=server2