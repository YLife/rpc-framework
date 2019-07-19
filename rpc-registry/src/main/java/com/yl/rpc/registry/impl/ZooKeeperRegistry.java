package com.yl.rpc.registry.impl;

import com.yl.constance.CommonConst;
import com.yl.rpc.registry.RpcRegistry;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

import static org.apache.zookeeper.Watcher.Event.KeeperState.SyncConnected;

public class ZooKeeperRegistry implements RpcRegistry {

    // 线程等待锁，保证zk连接建立成功，再继续后续操作
    private CountDownLatch latch = new CountDownLatch(1);

    @Override
    public ZooKeeper doConnection() throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper(CommonConst.ZKConst.ZOOKEEPER_ADDRESS,
                CommonConst.ZKConst.TIME_OUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState() == SyncConnected) {
                    latch.countDown();
                }
            }
        });
        latch.await();
        return zooKeeper;
    }

    @Override
    public void doRegistry(ZooKeeper zk, String serverInfo) throws Exception{
        // 判断父节点是否存在，不存在则新建
        if (zk.exists(CommonConst.ZKConst.PARENT_PATH, true) == null) {
            zk.create(CommonConst.ZKConst.PARENT_PATH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE
                    , CreateMode.PERSISTENT);
        }
        String subNode = zk.create(CommonConst.ZKConst.PARENT_PATH + "/" + serverInfo, serverInfo.getBytes()
                , ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("新建节点为：" + subNode);
    }
}
