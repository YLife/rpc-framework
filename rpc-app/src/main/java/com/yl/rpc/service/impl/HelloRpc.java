package com.yl.rpc.service.impl;

import com.yl.rpc.service.RpcService;

public class HelloRpc implements RpcService {

    @Override
    @com.yl.rpc.annotation.RpcService(serviceCode = "yl_RpcService_test")
    public String testRpc(String content) {
        System.out.println("Rpc receive：" + content);
        return "Rpc response: Hello！" ;
    }

}
