package com.yl.rpc.client;

import com.yl.coder.RpcDecoder;
import com.yl.coder.RpcEncoder;
import com.yl.constance.CommonConst;
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

    // 建立连接
    private void establishConneciton() throws InterruptedException {
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
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes("hello, server！".getBytes());
        future.channel().writeAndFlush(byteBuf);
        Thread.sleep(1000);
        System.out.println("--------");
    }

    public static void main(String[] args) throws InterruptedException {
        RpcClient client = new RpcClient();
        client.establishConneciton();
    }
}
