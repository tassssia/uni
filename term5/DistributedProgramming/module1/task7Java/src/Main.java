import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        int numCashiers = 4;
        int maxCash = 100;
        Thread[] threads = new Thread[numCashiers+1];

        ArrayList<Cashier> cashiers = new ArrayList<>();
        for (int i = 0; i < numCashiers; i++) {
            cashiers.add(new Cashier(50));
            threads[i] = new Thread(cashiers.get(i));
        }
        Observer observer = new Observer(cashiers, maxCash);
        threads[numCashiers] = new Thread(observer);

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
