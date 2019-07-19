package com.yl.rpc.registry;

import com.yl.spi.BaseServerLoader;

public class RegistryFactory {

    private static RpcRegistry registry;
    private static Boolean lock = Boolean.TRUE;

    public static RpcRegistry getInstance() {
        if (registry == null) {
            synchronized (lock) {
                if (Boolean.FALSE == lock) {
                    registry = BaseServerLoader.load(RpcRegistry.class);
                    lock = Boolean.TRUE;
                }
            }
        }
        return registry;
    }
}
