package com.yl.spi;

import java.util.ServiceLoader;

public class BaseServerLoader {

    // spi方式加载实现类
    public static<T> T load(Class<T> serviceClass) {
        return ServiceLoader.load(serviceClass).iterator().next();
    }

}
