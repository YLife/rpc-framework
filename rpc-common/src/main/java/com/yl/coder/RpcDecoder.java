package com.yl.coder;

import com.yl.constance.CommonConst;
import com.yl.message.impl.ResponseMessage;
import com.yl.serialization.RpcSerialization;
import com.yl.spi.BaseServerLoader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> clazz;

    public RpcDecoder(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 校验可读长度是否大于最小可读数据长度
        if (byteBuf.readableBytes() < CommonConst.Coder.MAIN_LENGTH) {
            return;
        }
        // 标记当前读指针位置
        byteBuf.markReaderIndex();
        int length = byteBuf.readInt();
        // 空包、半包都不做处理
        if (length <= 0) {
            channelHandlerContext.close();
        }
        if (byteBuf.readableBytes() < length) {
            byteBuf.resetReaderIndex();
            return;
        }
        // 将数据从buffer缓冲区读到字节数组中
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        // 将字节数组反序列化为对象
        Object object = BaseServerLoader.load(RpcSerialization.class)
                .deserialize(bytes, clazz);
        list.add(object);
    }

}
