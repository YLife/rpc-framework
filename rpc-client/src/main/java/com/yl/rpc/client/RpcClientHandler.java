package com.yl.rpc.client;

import com.yl.message.RpcMessage;
import com.yl.message.impl.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcClientHandler extends SimpleChannelInboundHandler<ResponseMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ResponseMessage responseMessage) throws Exception {
        // TODO 业务处理
        System.out.println("收到服务端响应...");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // TODO 异常处理
    }
}
