package com.yl.spi;

import java.util.ServiceLoader;
/**
 * @description SPI加载基础类
 *
 * @version v1.1.0
 * @author yanglun
 * @date  2019/7/14 1:20
 * Modification History:
 *   Date           Author          Version            Description
 *-------------------------------------------------------------
 *    2019/7/14      yanglun            v1.0.0              修改原因
 */
public class BaseServerLoader {

    // spi方式加载实现类
    public static <T> T load(Class<T> serviceClass) {
        return ServiceLoader.load(serviceClass).iterator().next();
    }

}
