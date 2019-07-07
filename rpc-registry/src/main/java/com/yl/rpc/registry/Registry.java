package com.yl.rpc.registry;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public interface Registry {

    /**
     * @Function: com.yl.rpc.registry.Registry::doConnection
     * @description 连接到zookeeper
     *
     * @throws Exception
     * @version v1.1.0
     * @author yanglun
     * @date  2019/7/7 23:07
     * Modification History:
     *   Date           Author          Version            Description
     *-------------------------------------------------------------
     *    2019/7/7      yanglun            v1.0.0              修改原因
     */
    ZooKeeper doConnection() throws Exception;

    void doRegistry(ZooKeeper zk, String serverInfo) throws Exception;

}
