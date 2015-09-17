import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerPopulator {
   public void go(int port) {
      try {
         ExecutorService pool = Executors.newCachedThreadPool();
         ServerSocket servsock = new ServerSocket(port);
         while (true) {// keep accepting connections, forever
            Socket s = servsock.accept();
            ServerDelegate sd = new ServerDelegate(s);
            pool.submit(sd);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
