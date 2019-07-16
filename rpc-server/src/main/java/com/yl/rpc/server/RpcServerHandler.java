package com.yl.rpc.server;

import com.yl.message.impl.RequestMessage;
import com.yl.rpc.wapper.ServiceWapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.util.Map;

public class RpcServerHandler extends SimpleChannelInboundHandler<RequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RequestMessage requestMessage) throws Exception {
        // TODO 业务逻辑处理
        System.out.println("收到客户端请求...");
        String serviceCode = requestMessage.getMethodName();
        Object[] paramters = requestMessage.getParamters();
        Map<String, ServiceWapper> serviceWapperMap = RpcServer.serviceWapperMap;
        if (!serviceWapperMap.containsKey(serviceCode)) {
            throw new Exception("服务【" + serviceCode + "】不存在！");
        }
        ServiceWapper wapper = serviceWapperMap.get(serviceCode);
        Class<?> clazz = wapper.getClazz();
        String methodName = wapper.getMethodName();
        Class<?>[] parameterTypes = wapper.getParameterTypes();
        Method method = clazz.getMethod(methodName, parameterTypes);
        method.invoke(clazz.newInstance(), paramters);
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes("hello, client！".getBytes());
        channelHandlerContext.writeAndFlush(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // TODO 异常处理
    }
}
