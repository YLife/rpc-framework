package com.yl.rpc.server;

import com.yl.coder.RpcDecoder;
import com.yl.coder.RpcEncoder;
import com.yl.constance.CommonConst;
import com.yl.message.impl.RequestMessage;
import com.yl.rpc.annotation.RpcService;
import com.yl.rpc.registry.RegistryFactory;
import com.yl.rpc.registry.RpcRegistry;
import com.yl.rpc.wapper.ServiceWrapper;
import com.yl.util.CommonUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.zookeeper.ZooKeeper;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class RpcServer {

    public final static Map<String, ServiceWrapper> serviceWrapperMap = new ConcurrentHashMap<>();
    private Object object;

    public RpcServer(Object object) {
        this.object = object;
    }

    public void start() throws Exception {
        // 服务编织
        createServiceWapper();
        // 启动Netty服务器
        startServer();
        System.out.println("服务端启动成功，绑定端口：" + CommonConst.ServerConst.PORT);
        // 注册服务器节点
    }

    // 启动netty服务器
    public void startServer() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1, new ThreadFactory() {
            private final AtomicInteger index = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("Accept-thread" + index.getAndIncrement());
                //thread.setDaemon(true);
                return thread;
            }
        });
        EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() << 1, new ThreadFactory() {
            private final AtomicInteger index = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("Nio-thread" + index.getAndIncrement());
                //thread.setDaemon(true);
                return thread;
            }
        });
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new RpcEncoder())
                                    .addLast(new RpcDecoder(RequestMessage.class))
                                    .addLast(new RpcServerHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(CommonConst.ServerConst.HOST, CommonConst.ServerConst.PORT).sync();
            future.channel().closeFuture();
        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    // 服务编织
    private Map createServiceWapper() throws Exception {
        Method[] methods = object.getClass().getMethods();
        RpcService rpcService;
        if (!CommonUtils.isEmptyArray(methods)) {
            for (Method method : methods) {
                rpcService = method.getAnnotation(RpcService.class);
                if (rpcService == null) {
                    continue;
                }
                String serviceCode = rpcService.serviceCode();
                if (CommonUtils.isEmptyString(serviceCode)) {
                    throw new Exception("服务名【serviceCode】不能为空！");
                }
                ServiceWrapper wrapper = new ServiceWrapper();
                wrapper.setServiceCode(serviceCode);
                wrapper.setClazz(object.getClass());
                wrapper.setMethodName(method.getName());
                wrapper.setParameterTypes(method.getParameterTypes());
                serviceWrapperMap.put(serviceCode, wrapper);
            }
        }
        return serviceWrapperMap;
    }

    // 注册服务器节点
    private void registryServerInfo() throws Exception {
        RpcRegistry registry = RegistryFactory.getInstance();
        ZooKeeper zooKeeper = registry.doConnection();
        registry.doRegistry(zooKeeper, CommonConst.ServerConst.HOST
                + ":" + CommonConst.ServerConst.PORT);
    }

    public static void main(String[] args) throws Exception {
        RpcServer server = new RpcServer(null);
        server.start();
    }
}
