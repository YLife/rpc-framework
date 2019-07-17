package com.yl.rpc.client;

import com.yl.coder.RpcDecoder;
import com.yl.coder.RpcEncoder;
import com.yl.constance.CommonConst;
import com.yl.message.impl.RequestMessage;
import com.yl.message.impl.ResponseMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.CountDownLatch;

public class RpcClient {

    private ResponseMessage responseMessage;
    // 线程等待锁，保证服务端响应后，再返回客户端
    private CountDownLatch latch = new CountDownLatch(1);

    public ResponseMessage sendMessage(RequestMessage rpcMessage) throws Exception {
        // 建立连接
        ChannelFuture future = establishConneciton();
        future.channel().writeAndFlush(rpcMessage);
        // 等待服务端响应
        latch.await();
        return responseMessage;
    }

    // 建立连接
    private ChannelFuture establishConneciton() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bossGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new RpcEncoder())
                                .addLast(new RpcDecoder(ResponseMessage.class))
                                .addLast(new RpcClientHandler());
                    }
                });
        ChannelFuture future = bootstrap.connect(CommonConst.ServerConst.HOST, CommonConst.ServerConst.PORT).sync();
        return future;
    }

    private class RpcClientHandler extends SimpleChannelInboundHandler<ResponseMessage> {

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ResponseMessage message) throws Exception {
            System.out.println("收到服务端响应...");
            System.out.println("响应id：" + message.getResponseId());
            System.out.println(message.getResult());
            responseMessage = message;
            // 释放线程等待锁
            latch.countDown();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            // TODO 异常处理
        }
    }

    public static void main(String[] args) throws Exception {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setRequestId("007");
        requestMessage.setMethodName("yl_RpcService_test");
        requestMessage.setParamters(new Object[]{"Hello, rpc！"});
        RpcClient client = new RpcClient();
        ResponseMessage responseMessage = client.sendMessage(requestMessage);
        System.out.println(responseMessage.getResult());
    }
}
