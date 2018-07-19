package nongui;

import java.io.IOException;
import java.net.ServerSocket;

import calculateprice.CalculatePrice;
/**
 *
 * @author Ahmed El-Torky
 */
public class Server {

    public static void main(String[] args) {
        try {
            System.out.println("Server Started ......");
            ServerSocket socket = new ServerSocket(1991);

            while (true) {
                Thread t = new Thread(new CalculatePrice(socket.accept(), 20, 10, 30));
                t.start();
            }

        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

}
