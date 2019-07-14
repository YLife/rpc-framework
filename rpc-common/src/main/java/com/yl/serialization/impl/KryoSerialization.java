package com.yl.serialization.impl;

import com.yl.serialization.RpcSerialization;

/**
 * @description 使用keyo进行序列化与反序列化
 *      1、效率高
 *      2、生成的码流小
 *      3、只针对java语言使用
 * @version v1.1.0
 * @author yanglun
 * @date  2019/7/14 1:18
 * Modification History:
 *   Date           Author          Version            Description
 *-------------------------------------------------------------
 *    2019/7/14      yanglun            v1.0.0              修改原因
 */
public class KryoSerialization implements RpcSerialization {

    @Override
    public <T> byte[] serialize(T object) {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> tClass) {
        return null;
    }

}
