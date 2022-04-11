package com.rpc.server;

import com.rpc.Request;
import com.rpc.common.utils.ReflectionUtils;

/**
 * 调用具体服务
 */
public class ServiceInvoker {

    public Object invoke(ServiceInstance serviceInstance, Request request){
        return ReflectionUtils.invoke(serviceInstance.getTarget(), serviceInstance.getMethod(), request.getParameter());

    }
}
