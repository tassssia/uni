import java.util.ArrayList;

public class Observer implements Runnable {
    private int maxCash;
    private ArrayList<Cashier> cashiers;
    private int numCashiers;

    public Observer(ArrayList<Cashier> cashiers, int maxCash) {
        this.maxCash = maxCash;
        this.cashiers = cashiers;
        this.numCashiers = cashiers.size();
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < numCashiers; i++) {
                // вважаємо, що сховище достатньо велике
                if (cashiers.get(i).getCash() == 0) {
                    cashiers.get(i).addCash(50);
                    notify();
                }
                else if (cashiers.get(i).getCash() > 99) {
                    cashiers.get(i).takeToVault();
                }
            }
        }
    }
}
