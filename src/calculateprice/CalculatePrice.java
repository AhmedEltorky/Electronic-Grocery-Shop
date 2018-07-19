package calculateprice;

import guiwithobservabledesignpattern.GUIServer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Ahmed El-Torky
 */
public class CalculatePrice implements Runnable {

    private static int applePrice;
    private static int bananaPrice;
    private static int orangePrice;
    private static final int GET_CHECKOUT_REQ = -1;
    private static final int GET_PRICE_REQ = -2;
    private static final int PRICE_CHANGE_ALERT = -3;
    private static final int TOTAL_PRICE_ALERT = -4;
    private static final int SET_PRICES_ALERT = -5;

    private Socket socket = null;
    private GUIServer server;
    private DataOutputStream outputData;
    private DataInputStream inputData;

    public CalculatePrice(Socket socket, int applePrice, int bananaPrice, int orangePrice) {
        this.socket = socket;
        CalculatePrice.setPrices(applePrice, bananaPrice, orangePrice);
    }

    public CalculatePrice(GUIServer server, Socket socket, int applePrice, int bananaPrice, int orangePrice) {
        this.server = server;
        this.socket = socket;
        CalculatePrice.setPrices(applePrice, bananaPrice, orangePrice);
        server.addObservers(this);
    }

    public static void setPrices(int applePrice, int bananaPrice, int orangePrice) {
        CalculatePrice.applePrice = applePrice;
        CalculatePrice.bananaPrice = bananaPrice;
        CalculatePrice.orangePrice = orangePrice;
    }

    public void sendNewPrices() {
        try {
            outputData.writeInt(PRICE_CHANGE_ALERT);
            outputData.writeInt(applePrice);
            outputData.writeInt(bananaPrice);
            outputData.writeInt(orangePrice);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public void run() {
        try {
            outputData = new DataOutputStream(socket.getOutputStream());
            inputData = new DataInputStream(socket.getInputStream());

            while (true) {
                switch (inputData.readInt()) {
                    case GET_PRICE_REQ:
                        outputData.writeInt(SET_PRICES_ALERT);
                        outputData.writeInt(applePrice);
                        outputData.writeInt(bananaPrice);
                        outputData.writeInt(orangePrice);
                        break;
                    case GET_CHECKOUT_REQ:
                        int apple = inputData.readInt();
                        int banana = inputData.readInt();
                        int orange = inputData.readInt();
                        int totalPrice = (apple * applePrice) + (banana * bananaPrice) + (orange * orangePrice);
                        outputData.writeInt(TOTAL_PRICE_ALERT);
                        outputData.writeInt(totalPrice);
                        break;
                    default:
                        break;
                }
            }

//            inputData.close();
//            outputData.close();
//            this.socket.close();
        } catch (IOException ex) {
            ex.toString();
        }
    }

}
