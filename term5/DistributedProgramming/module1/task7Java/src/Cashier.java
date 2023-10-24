import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.Queue;

public class Cashier implements Runnable {
    SecureRandom random;
    private static int numCashiers;
    private int id;
    private int cash;
    private Queue<Customer> queue = new LinkedList<>();

    public Cashier(int cash) {
        this.cash = cash;
        this.id = numCashiers;
        numCashiers++;

        for (int i = 0; i < 8; i++) {
            queue.add(new Customer(random.nextInt(100) + 100, random.nextInt(100), random.nextInt(5)));
        }
    }

    public synchronized int getCash() {
        return cash;
    }

    public synchronized void addCash(int amount) {
        cash += amount;
        System.out.println("Cashier " + id + " was replenished");
    }
    public synchronized void takeToVault() {
        cash = 50;
        System.out.println("Cash from cashier " + id + " was taken to vault");
    }

    private void processCustomer(Customer current) {
        switch (current.action) {
            case 0:
                current.withdraw(random.nextInt(20));
                break;
            case 1:
                current.topUp(random.nextInt(20));
                break;
            case 2:
                current.transfer(random.nextInt(20));
                break;
            case 3:
                current.pay(random.nextInt(20));
                break;
            case 4:
                current.exchange(random.nextInt(20));
                break;
        }
    }

    @Override
    public void run() {
        while (!queue.isEmpty()) {
            Customer current = queue.poll();
            try {
                Thread.sleep(500);

            } catch (Exception e) {
                e.printStackTrace();
            }
            processCustomer(current);
            if (cash == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
