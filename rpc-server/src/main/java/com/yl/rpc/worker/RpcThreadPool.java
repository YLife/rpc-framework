package com.yl.rpc.worker;

import java.util.concurrent.Executor;

/**
 * @description rpc业务线程池
 *
 * @version v1.1.0
 * @author yanglun
 * @date  2019/7/18 23:56
 * Modification History:
 *   Date           Author          Version            Description
 *-------------------------------------------------------------
 *    2019/7/18      yanglun            v1.0.0              修改原因
 */
public interface RpcThreadPool {

    // 获取业务线程池
    Executor getExecutor();

}
