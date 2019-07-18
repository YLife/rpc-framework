package com.yl.rpc.worker.impl;

import com.yl.message.impl.RequestMessage;
import com.yl.message.impl.ResponseMessage;
import com.yl.rpc.server.RpcServer;
import com.yl.rpc.wapper.ServiceWrapper;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;
import java.util.Map;

public class WorkerTask implements Runnable {

    private ChannelHandlerContext channelHandlerContext;
    private RequestMessage requestMessage;

    public WorkerTask(ChannelHandlerContext channelHandlerContext, RequestMessage requestMessage) {
        this.channelHandlerContext = channelHandlerContext;
        this.requestMessage = requestMessage;
    }

    @Override
    public void run() {
        try {
            String serviceCode = requestMessage.getMethodName();
            Object[] paramters = requestMessage.getParamters();
            Map<String, ServiceWrapper> serviceWrapperMap = RpcServer.serviceWrapperMap;
            if (!serviceWrapperMap.containsKey(serviceCode)) {
                System.out.println("服务【" + serviceCode + "】不存在！");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
