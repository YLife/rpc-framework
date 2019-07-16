package com.yl.rpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 暴露rpc服务的注解
 *
 * @version v1.1.0
 * @author yanglun
 * @date  2019/7/16 20:15
 * Modification History:
 *   Date           Author          Version            Description
 *-------------------------------------------------------------
 *    2019/7/16      yanglun            v1.0.0              修改原因
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcService {

    String serviceCode();// 服务名：yl_接口名_方法名

}
