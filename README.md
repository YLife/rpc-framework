# rpc-framework
##这些天复习netty，正好也在研究公司的rpc框架，因此即兴开发了rpc-framework这款简易的rpc框架。  整体架构，类似dubbo，如图：  

![如未看到图片，可能是少儿不宜](https://github.com/YLife/rpc-framework/blob/master/image/dubbo.png)图片来自dubbo

###技术选型：  
```
rpc通信框架：netty；  
注册中心：目前选择的zookeeper，支持spi动态扩展；  
序列化：目前选择的Protostaff、Kryo，支持spi动态扩展；  
```
###框架构成：  
```
rpc-app：应用模块，该模块其实应该不算rpc框架中的一部分了，只是当时为了方便测试，在下面建了一个应用模块；
rpc-common：公共模块，主要处理序列化、编解码、spi、工具类、公共常量相关的一些列东西；  
rpc-client：客户端模块，封装客户端调用api；  
rpc-registry：注册模块，实现服务注册、服务发现、服务推送；  
rpc-server：服务端模块，启动netty服务器，向注册中心上报服务节点信息；
```
###未完成部分：  
```
rpc-spring-support：spring支持，其实最开始图方便是想用spring起完成服务端启动的，但是想想，这样就跟spring耦合上了，便自己开发了一个starter，后续会追加一个模块添加spirng支持；  
rpc-monitor：监控中心；  
```
###缺陷：  
```
1、很多课配置化的东西（服务器信息、zk地址等）未实现配置化；  
2、服务熔断、限流这块也未引入；  
3、由于时间原因，很多代码解耦、设计上可能还有很多可优化的地方；  
```
###感悟：  
```
虽然这只是个简易的rpc框架，但通过这次的开发，对整个rpc通信的原理了解更透彻了，平常很少考虑的并发问题也在此得到了练习。
哈哈，其实还有一点，通过写这些，才发现自己有多么菜，真的是个弟弟，原谅此处不知道怎么打笑脸。无论如何，一点一滴进步吧，后面有时间再来完善此框架。
```
