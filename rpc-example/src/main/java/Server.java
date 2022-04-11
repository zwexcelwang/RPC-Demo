import com.rpc.server.RpcServer;
import com.rpc.server.RpcServerConfig;

public class Server {
    public static void main(String[] args) throws Exception {
        RpcServer server = new RpcServer();
        server.register(CalService.class, new CalServiceImpl());
        server.start();
    }
}
