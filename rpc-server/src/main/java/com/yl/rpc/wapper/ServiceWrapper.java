package com.yl.rpc.wapper;

/**
 * @author yanglun
 * @version v1.1.0
 * @description 服务编织
 * @date 2019/7/16 22:42
 * Modification History:
 * Date           Author          Version            Description
 * -------------------------------------------------------------
 * 2019/7/16      yanglun            v1.0.0              修改原因
 */
public class ServiceWrapper {

    private String serviceCode;// 服务名
    private Class<?> clazz;// 类名
    private String methodName;// 方法名
    private Class<?>[] parameterTypes;// 参数类型集合
    private Object[] parameters;// 参数值集合

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
