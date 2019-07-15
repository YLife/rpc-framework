package com.yl.message.impl;

import com.yl.message.RpcMessage;

/**
 * @description 封装请求消息
 *
 * @version v1.1.0
 * @author yanglun
 * @date  2019/7/14 0:56
 * Modification History:
 *   Date           Author          Version            Description
 *-------------------------------------------------------------
 *    2019/7/14      yanglun            v1.0.0              修改原因
 */
public class RequestMessage implements RpcMessage {

    private String requestId;
    private String className;
    private String methodName;
    private Class<?>[] paramterTypes;
    private Object[] paramters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamterTypes() {
        return paramterTypes;
    }

    public void setParamterTypes(Class<?>[] paramterTypes) {
        this.paramterTypes = paramterTypes;
    }

    public Object[] getParamters() {
        return paramters;
    }

    public void setParamters(Object[] paramters) {
        this.paramters = paramters;
    }
}
