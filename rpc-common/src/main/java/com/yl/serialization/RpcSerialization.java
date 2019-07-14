package com.yl.serialization;

/**
 * @description rpc序列化
 *
 * @version v1.1.0
 * @author yanglun
 * @date  2019/7/14 1:06
 * Modification History:
 *   Date           Author          Version            Description
 *-------------------------------------------------------------
 *    2019/7/14      yanglun            v1.0.0              修改原因
 */
public interface RpcSerialization {

    /**
     * @Function: com.yl.serialization.RpcSerialization::serialize
     * @description 将对象序列化为子节数组
     *
     * @version v1.1.0
     * @author yanglun
     * @date  2019/7/14 1:08
     * Modification History:
     *   Date           Author          Version            Description
     *-------------------------------------------------------------
     *    2019/7/14      yanglun            v1.0.0              修改原因
     */
    <T> byte[] serialize(T object);


    /**
     * @Function: com.yl.serialization.RpcSerialization::deserialize
     * @description 将子节数组序反序列化为对象
     *
     * @version v1.1.0
     * @author yanglun
     * @date  2019/7/14 1:10
     * Modification History:
     *   Date           Author          Version            Description
     *-------------------------------------------------------------
     *    2019/7/14      yanglun            v1.0.0              修改原因
     */
    <T> T deserialize(byte[] bytes, Class<T> tClass);
}
