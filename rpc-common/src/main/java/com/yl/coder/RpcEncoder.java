package com.yl.coder;

import com.yl.message.RpcMessage;
import com.yl.serialization.RpcSerialization;
import com.yl.spi.BaseServerLoader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RpcEncoder extends MessageToByteEncoder<RpcMessage> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage, ByteBuf byteBuf) throws Exception {
        byte[] bytes = BaseServerLoader.load(RpcSerialization.class).serialize(rpcMessage);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

}
