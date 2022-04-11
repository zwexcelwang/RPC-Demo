package com.rpc.transport;

import com.rpc.Peer;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * 1.创建连接
 * 2.发送数据，并且等待响应
 * 3.关闭连接
 */
public interface TransportClient {
    void connect(Peer peer);

    InputStream write(InputStream data) throws IOException;

    void close();
}
