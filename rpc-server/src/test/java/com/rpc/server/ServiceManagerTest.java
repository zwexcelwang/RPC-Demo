package com.rpc.server;

import com.rpc.Request;
import com.rpc.ServiceDescriptor;
import com.rpc.common.utils.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.sql.Ref;

import static org.junit.Assert.*;

public class ServiceManagerTest {
    ServiceManager serviceManager;
    @Before
    public void init() {
        serviceManager = new ServiceManager();
        TestInterface bean = new TestClass();
        serviceManager.register(TestInterface.class, bean);
    }
    @Test
    public void register() {
        TestInterface bean = new TestClass();
        serviceManager.register(TestInterface.class, bean);
    }

    @Test
    public void lookup() {

        Method[] methods = ReflectionUtils.getPublicMethods(TestInterface.class);
        ServiceDescriptor serviceDescriptor = ServiceDescriptor.from(TestInterface.class, methods[0]);
        Request request = new Request();
        request.setService(serviceDescriptor);

        ServiceInstance serviceInstance = serviceManager.lookup(request);
        assertNotNull(serviceInstance);

    }
}