package guiwithobservabledesignpattern;

import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 * @author Ahmed El-Torky
 */
public class ClientPriceObserver implements Runnable {

    private GUIClient client;
    private DataInputStream disCS;
    private static final int PRICE_CHANGE_ALERT = -3;
    private static final int TOTAL_PRICE_ALERT = -4;
    private static final int SET_PRICES_ALERT = -5;

    public ClientPriceObserver(GUIClient client, DataInputStream disCS) {
        this.client = client;
        this.disCS = disCS;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int wait = disCS.readInt();
                switch (wait) {
                    case PRICE_CHANGE_ALERT:
                        client.setPrices(disCS.readInt(), disCS.readInt(), disCS.readInt());
                        break;
                    case TOTAL_PRICE_ALERT:
                        client.setTotalPriceLable(disCS.readInt());
                        break;
                    case SET_PRICES_ALERT:
                        client.setPrices(disCS.readInt(), disCS.readInt(), disCS.readInt());
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

}
