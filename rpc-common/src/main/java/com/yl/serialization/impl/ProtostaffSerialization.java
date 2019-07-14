package com.yl.serialization.impl;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.yl.serialization.RpcSerialization;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description protostaff序列化方式
 *      支持跨语言使用
 * @version v1.1.0
 * @author yanglun
 * @date  2019/7/14 23:08
 * Modification History:
 *   Date           Author          Version            Description
 *-------------------------------------------------------------
 *    2019/7/14      yanglun            v1.0.0              修改原因
 */
public class ProtostaffSerialization implements RpcSerialization {

    // 反射构造实例，可绕过无参构造
    private Objenesis objenesis = new ObjenesisStd(true);
    // 由于构建schema的过程较缓慢，使用map缓存构造的schema
    private Map<Class<?>, Schema<?>> cachedMap = new ConcurrentHashMap<Class<?>, Schema<?>>();

    @Override
    public <T> byte[] serialize(T object) {
        Class<T> cls = (Class<T>) object.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            return ProtostuffIOUtil.toByteArray(object, getSchema(cls), buffer);
        } catch (Exception e) {
            throw e;
        } finally {
            buffer.clear();
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> tClass) {
        T object = objenesis.newInstance(tClass);
        ProtostuffIOUtil.mergeFrom(bytes, object, getSchema(tClass));
        return object;
    }

    // 构建schema实例
    private <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cachedMap.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.getSchema(cls);
            if (schema != null) {
                cachedMap.put(cls, schema);
            }
        }
        return schema;
    }

    public static void main(String[] args) {
        ProtostaffSerialization serialization = new ProtostaffSerialization();
        byte[] bytes = serialization.serialize("11111");
        System.out.println(bytes);
        String str = serialization.deserialize(bytes, String.class);
        System.out.println(str);
    }
}
