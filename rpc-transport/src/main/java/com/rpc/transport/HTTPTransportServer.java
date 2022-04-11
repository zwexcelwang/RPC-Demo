package com.rpc.transport;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class HTTPTransportServer implements TransportServer{

    private  RequestHandler requestHandler;
    private Server server;
    @Override
    public void init(int port, RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        this.server = new Server(port);
        //servlet接收请求
        ServletContextHandler ctx = new ServletContextHandler();
        server.setHandler(ctx);
        ServletHolder servletHolder = new ServletHolder(new RequestServlet());
        ctx.addServlet(servletHolder, "/*");
    }

    @Override
    public void start() {
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    class RequestServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            log.info("client connect");
            InputStream inputStream = req.getInputStream();
            OutputStream outputStream = resp.getOutputStream();

            if(requestHandler != null) {
               requestHandler.onRequest(inputStream, outputStream);
            }
           outputStream.flush();
        }
    }
}
