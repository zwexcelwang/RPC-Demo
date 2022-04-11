package com.rpc.transport;

import com.rpc.Peer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPTransportClient implements TransportClient{
    private String url;

    @Override
    public void connect(Peer peer) {
        this.url = "http://" + peer.getHost() + ":" + peer.getPort();
    }

    @Override
    public InputStream write(InputStream data) throws IOException {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);;
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();
            IOUtils.copy(data, httpURLConnection.getOutputStream());
            int resCode = httpURLConnection.getResponseCode();
            if(resCode == HttpURLConnection.HTTP_OK){
                return httpURLConnection.getInputStream();
            }else {
                return httpURLConnection.getErrorStream();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void close() {

    }
}
