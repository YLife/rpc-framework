package com.yl;

import com.yl.rpc.server.RpcServer;
import com.yl.rpc.service.impl.HelloRpc;

public class RpcStarter {

    public static void main(String[] args) throws Exception {
        RpcServer rpcServer = new RpcServer(new HelloRpc());
        rpcServer.start();
    }
}
