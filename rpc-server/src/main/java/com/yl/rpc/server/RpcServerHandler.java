package com.yl.rpc.server;

import com.yl.message.impl.RequestMessage;
import com.yl.rpc.worker.RpcThreadPoolFactory;
import com.yl.rpc.worker.impl.WorkerTask;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcServerHandler extends SimpleChannelInboundHandler<RequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, final RequestMessage requestMessage) throws Exception {
        // TODO 业务逻辑处理
        System.out.println(Thread.currentThread().getName() + "收到客户端请求...");
        System.out.println("请求id：" + requestMessage.getRequestId());
        RpcThreadPoolFactory.createRpcThreadPool().execute(new WorkerTask(channelHandlerContext, requestMessage));
        System.out.println("服务端处理完毕...");
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
