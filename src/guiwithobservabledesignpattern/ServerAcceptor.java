package guiwithobservabledesignpattern;

import java.io.IOException;
import java.net.ServerSocket;
import calculateprice.CalculatePrice;

/**
 *
 * @author Ahmed El-Torky
 */
public class ServerAcceptor implements Runnable {

    private ServerSocket socket;
    private GUIServer server;

    public ServerAcceptor(GUIServer server, ServerSocket socket) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            System.out.println("server acceptor for: " + server.getTitle());
            while (true) {
                Thread t = new Thread(new CalculatePrice(server, socket.accept(), server.getApplePrice(), server.getBananaPrice(), server.getOrangePrice()));
                t.start();
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

}
