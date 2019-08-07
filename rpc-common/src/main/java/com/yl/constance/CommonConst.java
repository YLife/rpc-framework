package com.yl.constance;

public interface CommonConst {

    // zookeeper相关常量
    interface ZKConst {
        String ZOOKEEPER_ADDRESS = "192.168.243.136:2181,192.168.243.133:2181,192.168.243.134:2181";// zookeeper地址
        String PARENT_PATH = "/servers";// 父路径
        int TIME_OUT = 5000;// 超时时间
    }

    interface ServerConst {
        String HOST = "127.0.0.1";
        int PORT = 39999;
    }

    interface Coder {
        // 最小可读数据包长度
        int MAIN_LENGTH = 4;
    }

    enum LoadBalance {
        RANDOM, // 随机
        CONSISTENCY_HASH // 一致性hash
    }
}
