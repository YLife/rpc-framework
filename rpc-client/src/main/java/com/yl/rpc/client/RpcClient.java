package com.yl.rpc.client;

import com.yl.coder.RpcDecoder;
import com.yl.coder.RpcEncoder;
import com.yl.constance.CommonConst;
import com.yl.message.RpcMessage;
import com.yl.message.impl.RequestMessage;
import com.yl.message.impl.ResponseMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class RpcClient {

    public ResponseMessage sendMessage(RequestMessage rpcMessage) throws Exception {
        // 建立连接
        ChannelFuture future = establishConneciton();
        //future.channel().writeAndFlush(rpcMessage);
        return null;
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
                                .addLast(new RpcDecoder())
                                .addLast(new RpcClientHandler());
                    }
                });
        ChannelFuture future = bootstrap.connect(CommonConst.ServerConst.HOST, CommonConst.ServerConst.PORT).sync();
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setMethodName("yl_RpcService_test");
        requestMessage.setParamters(new Object[]{"1111"});
        requestMessage.setParamters(new Class<?>[]{String.class});
        future.channel().writeAndFlush(requestMessage);
        Thread.sleep(1000);
        System.out.println("--------");
        return future;
    }

    public static void main(String[] args) throws Exception {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setMethodName("yl_RpcService_test");
        requestMessage.setParamters(new Object[]{"1111"});
        requestMessage.setParamters(new Class<?>[]{String.class});
        RpcClient client = new RpcClient();
        ResponseMessage responseMessage = client.sendMessage(requestMessage);
        System.out.println(responseMessage);
    }
}
