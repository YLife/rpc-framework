package com.yl.rpc.server;

import com.yl.message.impl.RequestMessage;
import com.yl.message.impl.ResponseMessage;
import com.yl.rpc.wapper.ServiceWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.util.Map;

public class RpcServerHandler extends SimpleChannelInboundHandler<RequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RequestMessage requestMessage) throws Exception {
        // TODO 业务逻辑处理
        System.out.println(Thread.currentThread().getName() + "收到客户端请求...");
        System.out.println("请求id：" + requestMessage.getRequestId());
        String serviceCode = requestMessage.getMethodName();
        Object[] paramters = requestMessage.getParamters();
        Map<String, ServiceWrapper> serviceWrapperMap = RpcServer.serviceWrapperMap;
        if (!serviceWrapperMap.containsKey(serviceCode)) {
            throw new Exception("服务【" + serviceCode + "】不存在！");
        }
        ServiceWrapper wrapper = serviceWrapperMap.get(serviceCode);
        Class<?> clazz = wrapper.getClazz();
        String methodName = wrapper.getMethodName();
        Class<?>[] parameterTypes = wrapper.getParameterTypes();
        Method method = clazz.getMethod(methodName, parameterTypes);
        method.invoke(clazz.newInstance(), paramters);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setResponseId(requestMessage.getRequestId());
        responseMessage.setResult("Rpc return：success！");
        channelHandlerContext.writeAndFlush(responseMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // TODO 异常处理
        try {
            throw cause;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
