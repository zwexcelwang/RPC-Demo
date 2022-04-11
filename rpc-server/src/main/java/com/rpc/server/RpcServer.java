package com.rpc.server;

import com.rpc.Request;
import com.rpc.Response;
import com.rpc.codec.Decoder;
import com.rpc.codec.Encoder;
import com.rpc.common.utils.ReflectionUtils;
import com.rpc.transport.RequestHandler;
import com.rpc.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class RpcServer {

    private RpcServerConfig rpcServerConfig;
    private TransportServer net;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    public RpcServer(){
        this(new RpcServerConfig());
    }
    public RpcServer(RpcServerConfig rpcServerConfig) {
        this.rpcServerConfig = rpcServerConfig;
        this.net = ReflectionUtils.newInstance(rpcServerConfig.getTransportClass());
        this.net.init(rpcServerConfig.getPort(), this.handler);
        this.encoder = ReflectionUtils.newInstance(rpcServerConfig.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(rpcServerConfig.getDecoderClass());

        this.serviceManager = new ServiceManager();
        this.serviceInvoker = new ServiceInvoker();

    }

    public void start() throws Exception {
        this.net.start();
    }

    public void stop() throws Exception {
        this.net.stop();
    }

    public <T> void register(Class<T> interfaceClass, T bean){
        serviceManager.register(interfaceClass, bean);
    }

    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream receive, OutputStream toResp) throws IOException {
            Response response = new Response();
            try {
                byte[] inBytes = IOUtils.readFully(receive, receive.available());
                Request request = decoder.decode(inBytes, Request.class);
                log.info("get request:{}",  request);
                ServiceInstance serviceInstance = serviceManager.lookup(request);
                Object ret = serviceInvoker.invoke(serviceInstance, request);
                response.setData(ret);
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
                response.setCode(1);
                response.setMessage("RpcServer got error: " + e.getClass().getName() + ": " + e.getMessage());

            } finally {
                byte[] outBytes = encoder.encode(response);
                toResp.write(outBytes);
                log.info("responsed client");
            }

        }
    };
}
