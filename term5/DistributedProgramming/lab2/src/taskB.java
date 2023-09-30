import java.security.SecureRandom;
import java.util.Stack;
import java.util.concurrent.SynchronousQueue;

class PieceOfProperty {
    private int id;
    private int cost;

    public PieceOfProperty (int id, int cost) {
        this.id = id;
        this.cost = cost;
    }


    public int getId() {
        return id;
    }
    public int getCost() {
        return cost;
    }
}

public class taskB {
    private SecureRandom random = new SecureRandom();
    private int statePropertyNum;
    private final Stack<PieceOfProperty> stateProperty = new Stack<PieceOfProperty>();
    private static int minCost = 10;
    private static int maxCost = 200;
    private SynchronousQueue<PieceOfProperty> toLoad = new SynchronousQueue<PieceOfProperty>();
    private SynchronousQueue<PieceOfProperty> toCount = new SynchronousQueue<PieceOfProperty>();

    public taskB(int statePropertyNum) {
        this.statePropertyNum = statePropertyNum;
        for (int i = 0; i < statePropertyNum; i++) {
            stateProperty.push(new PieceOfProperty(i, random.nextInt(maxCost - minCost) + minCost)) ;
        }
    }

    public void IvanovAct() throws InterruptedException {
        PieceOfProperty current;
        int taken = 0;
        while (taken != statePropertyNum) {
            current = stateProperty.pop();
            System.out.println("Ivanov has taken item " + current.getId());
            this.toLoad.put(current);
            taken++;

            //Thread.sleep(400);
        }
    }
    public void PetrovAct() throws InterruptedException {
        PieceOfProperty current;
        int loaded = 0;
        while (loaded != statePropertyNum) {
            current = this.toLoad.take();
            System.out.println("Petrov is loading item " + current.getId());
            this.toCount.put(current);
            loaded++;

            //Thread.sleep(500);
        }
    }
    public int NechyporchukAct() throws InterruptedException {
        PieceOfProperty current;
        int totalCost = 0;
        int counted = 0;
        while (counted != statePropertyNum) {
            current = this.toCount.take();
            System.out.println("Nechyporchuk has added cost of item " + current.getId());
            totalCost += current.getCost();
            counted++;

            //Thread.sleep(1200);
        }

        return totalCost;
    }

    public static void main(String[] args) throws InterruptedException {
        taskB b = new taskB(10);

        Thread Ivanov = new Thread(() -> {
            try {
                b.IvanovAct();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread Petrov = new Thread(() -> {
            try {
                b.PetrovAct();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread Nechyporchuk = new Thread(() -> {
            try {
                b.NechyporchukAct();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Ivanov.start();
        Petrov.start();
        Nechyporchuk.start();

        Ivanov.join();
        Petrov.join();
        Nechyporchuk.join();
    }
}
