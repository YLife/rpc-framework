package com.yl.rpc.worker;

import com.yl.rpc.worker.impl.WorkerThreadPool;

import java.util.concurrent.Executor;

/**
 * @author yanglun
 * @version v1.1.0
 * @description 线程池工厂
 * @date 2019/7/19 0:15
 * Modification History:
 * Date           Author          Version            Description
 * -------------------------------------------------------------
 * 2019/7/19      yanglun            v1.0.0              修改原因
 */
public class RpcThreadPoolFactory {

    private static Boolean lock;
    private static Executor executor;

    public static Executor createRpcThreadPool() {
        if (executor == null) {
            synchronized (lock) {
                if (Boolean.FALSE == lock) {
                    executor = new WorkerThreadPool().getExecutor();
                }
            }
        }
        return executor;
    }

    public static Executor createRpcThreadPool(int nThread, int queneSize) {
        if (executor == null) {
            synchronized (lock) {
                if (Boolean.FALSE == lock) {
                    executor = new WorkerThreadPool(nThread, queneSize).getExecutor();
                }
            }
        }
        return executor;
    }

    static {
        lock = Boolean.FALSE;
    }
}
