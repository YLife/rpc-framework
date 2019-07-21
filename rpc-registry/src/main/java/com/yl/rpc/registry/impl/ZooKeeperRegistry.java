package com.yl.rpc.registry.impl;

import com.yl.constance.CommonConst;
import com.yl.rpc.registry.RpcRegistry;
import org.apache.zookeeper.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;

import static org.apache.zookeeper.Watcher.Event.KeeperState.SyncConnected;

public class ZooKeeperRegistry implements RpcRegistry {

    // 线程等待锁，保证zk连接建立成功，再继续后续操作
    private final CountDownLatch latch = new CountDownLatch(1);
    // 缓存zookeeper连接
    private final Map<String, ZooKeeper> zooKeeperCache = new ConcurrentHashMap<>();
    // 缓存服务节点信息
    private final Set<String> serverCache = new CopyOnWriteArraySet<>();

    @Override
    public ZooKeeper doConnection() throws Exception {
        // 先从缓存获取zookeeper
        ZooKeeper zooKeeper = zooKeeperCache.get(CommonConst.ZKConst.ZOOKEEPER_ADDRESS);
        if (zooKeeper != null) {
            return zooKeeper;
        }
        // 缓存中未取到，则新建zookeeper连接，此处使用同步锁，防止创建多个zookeeper连接
        synchronized (zooKeeperCache) {
            if ((zooKeeper = zooKeeperCache.get(CommonConst.ZKConst.ZOOKEEPER_ADDRESS)) != null) {
                return zooKeeper;
            }
            zooKeeper = new ZooKeeper(CommonConst.ZKConst.ZOOKEEPER_ADDRESS,
                    CommonConst.ZKConst.TIME_OUT, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getState() == SyncConnected) {
                        System.out.println("连接zookeeper成功...");
                        latch.countDown();
                    }
                }
            });
            latch.await();
            zooKeeperCache.put(CommonConst.ZKConst.ZOOKEEPER_ADDRESS, zooKeeper);
        }
        return zooKeeper;
    }

    @Override
    public void doRegistry(String serverInfo) throws Exception{
        ZooKeeper zooKeeper = doConnection();
        // 判断父节点是否存在，不存在则新建
        if (zooKeeper.exists(CommonConst.ZKConst.PARENT_PATH, true) == null) {
            zooKeeper.create(CommonConst.ZKConst.PARENT_PATH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE
                    , CreateMode.PERSISTENT);
        }
        String subNode = zooKeeper.create(CommonConst.ZKConst.PARENT_PATH + "/" + serverInfo, serverInfo.getBytes()
                , ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("新建节点为：" + subNode);
    }

    @Override
    public Set<String> doSubscrib() throws Exception {
        ZooKeeper zooKeeper = zooKeeperCache.get(CommonConst.ZKConst.ZOOKEEPER_ADDRESS);
        List<String> serverList = zooKeeper.getChildren(CommonConst.ZKConst.PARENT_PATH, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                // 非服务节点信息变化，不做更新
                if (Event.EventType.NodeChildrenChanged != watchedEvent.getType()) {
                    return;
                }
                try {
                    doSubscrib();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        if (serverList != null && serverList.size() > 0) {
            serverCache.addAll(serverList);
        }
        return serverCache;
    }

    @Override
    public String discoverServer(CommonConst.LoadBalance loadBalance, String service) throws Exception {
        if (serverCache.size() > 0) {
            int index, count = 0;
            if (CommonConst.LoadBalance.RANDOM == loadBalance) {
                index = (int) (Math.random() * serverCache.size());
            } else if (CommonConst.LoadBalance.CONSISTENCY_HASH == loadBalance) {
                index = service.hashCode() % serverCache.size();
            } else {
                System.out.println("暂时不支持的负载均衡策略，后续更新...");
                return null;
            }
            for (String server : serverCache) {
                if (count == index) {
                    return server;
                }
                count++;
            }
        }
        System.out.println("暂时没有可用的服务节点...");
        return null;
    }

}
