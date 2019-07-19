package com.yl.constance;

public interface CommonConst {

    // zookeeper相关常量
    interface ZKConst {
        String ZOOKEEPER_ADDRESS = "";// zookeeper地址
        String PARENT_PATH = "/server";// 父路径
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

}
