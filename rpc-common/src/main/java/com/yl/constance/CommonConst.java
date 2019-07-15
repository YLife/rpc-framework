package com.yl.constance;

public interface CommonConst {

    // zookeeper相关常量
    interface ZKConst {
        String PARENT_PATH = "/server";// 父路径
    }

    interface ServerConst {
        String HOST = "127.0.0.1";
        int PORT = 8080;
    }

    interface Coder {
        // 最小可读数据包长度
        int MAIN_LENGTH = 4;
    }

}
