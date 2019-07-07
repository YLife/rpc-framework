package com.yl.rpc.registry;

import com.yl.spi.BaseServerLoader;

public class RegistryFactory {

    private Registry registry;
    private Boolean lock = Boolean.TRUE;

    public Registry getInstance() {
        if (registry == null) {
            synchronized (lock) {
                if (Boolean.FALSE == lock) {
                    registry = BaseServerLoader.load(Registry.class);
                    lock = Boolean.TRUE;
                }
            }
        }
        return registry;
    }
}
