package com.yl.rpc.registry;

import com.yl.constance.CommonConst;
import org.apache.zookeeper.ZooKeeper;

import java.util.Set;

/**
 * @author yanglun
 * @description: 注册中心
 *
 * @return
 * @date 2019/8/7 22:21
 */
public interface RpcRegistry {

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

    /**
     * @author yanglun
     * @description: 注册服务
     * @param serverInfo 服务信息
     * @return
     * @date 2019/8/7 22:27
     */
    void doRegistry(String serverInfo) throws Exception;

    /**
     * @author yanglun
     * @description: 订阅服务
     *
     * @return Set<String> 服务列表
     * @date 2019/8/7 22:28
     */
    Set<String> doSubscrib() throws Exception;

    /**
     * @author yanglun
     * @description: 发现服务
     * @param loadBalance 负载均衡策略
     * @param service 服务名
     *
     * @return 可用服务节点
     * @date 2019/8/7 22:29
     */
    String discoverServer(CommonConst.LoadBalance loadBalance, String service) throws Exception;
}
