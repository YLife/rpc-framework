package com.yl.rpc.server;

import com.yl.coder.RpcDecoder;
import com.yl.coder.RpcEncoder;
import com.yl.constance.CommonConst;
import com.yl.rpc.annotation.RpcService;
import com.yl.rpc.wapper.ServiceWapper;
import com.yl.util.CommonUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RpcServer {

    public final static Map<String, ServiceWapper> serviceWapperMap = new ConcurrentHashMap<>();
    private Object object;

    public RpcServer(Object object) {
        this.object = object;
    }

    public void start() throws Exception {
        createServiceWapper();
        startServer();
    }

    // 启动netty服务器
    public void startServer() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
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
                                    .addLast(new RpcDecoder())
                                    .addLast(new RpcServerHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(CommonConst.ServerConst.HOST, CommonConst.ServerConst.PORT).sync();
            future.channel().closeFuture();
        } catch (InterruptedException e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

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
                ServiceWapper wapper = new ServiceWapper();
                wapper.setServiceCode(serviceCode);
                wapper.setClazz(object.getClass());
                wapper.setMethodName(method.getName());
                wapper.setParameters(method.getParameterTypes());
                serviceWapperMap.put(serviceCode, wapper);
            }
        }
        return serviceWapperMap;
    }

    public static void main(String[] args) throws Exception {
        RpcServer server = new RpcServer(null);
        server.start();
    }
}
