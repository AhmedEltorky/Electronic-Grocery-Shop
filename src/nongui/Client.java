package nongui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Ahmed El-Torky
 */
public class Client {

    private static final int GET_CHECKOUT_REQ = -1;
    private static final int TOTAL_PRICE_ALERT = -4;

    public static void main(String args[]) {
        try {
            Socket clientSocket = new Socket("localhost", 1991);
            DataOutputStream dosCS = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream disCS = new DataInputStream(clientSocket.getInputStream());

            dosCS.writeInt(GET_CHECKOUT_REQ);
            System.out.println("please enter apple quantity:");
            dosCS.writeInt(Integer.parseInt(new Scanner(System.in).nextLine()));
            System.out.println("please enter banana quantity:");
            dosCS.writeInt(Integer.parseInt(new Scanner(System.in).nextLine()));
            System.out.println("please enter orange quantity:");
            dosCS.writeInt(Integer.parseInt(new Scanner(System.in).nextLine()));

            if (TOTAL_PRICE_ALERT == disCS.readInt()) {
                System.out.println("total price = " + disCS.readInt() + " £");
            }

            dosCS.close();
            disCS.close();
            clientSocket.close();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

}
