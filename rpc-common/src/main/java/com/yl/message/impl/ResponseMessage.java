package com.yl.message.impl;

import com.yl.message.RpcMessage;

/**
 * @description 封装响应消息
 *
 * @version v1.1.0
 * @author yanglun
 * @date  2019/7/14 0:58
 * Modification History:
 *   Date           Author          Version            Description
 *-------------------------------------------------------------
 *    2019/7/14      yanglun            v1.0.0              修改原因
 */
public class ResponseMessage implements RpcMessage {

    private String responseId;
    private Object result;

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
