import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.security.SecureRandom;

class Bear implements Runnable {
    private Pot pot;
    private volatile boolean isAwake;

    public Bear(Pot pot) {
        this.pot = pot;
        this.isAwake = false;
    }

    public synchronized void wakeUpBear() {
        isAwake = true;
        notify();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                while (!isAwake){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                pot.eat();
                isAwake = false;
                notifyAll();
            }
        }
    }
}

class Bee implements Runnable {
    private static int num = 0;
    private int id;
    private Pot pot;
    private Bear bear;
    private static SecureRandom random = new SecureRandom();

    public Bee(Pot pot, Bear bear) {
        id = num++;
        this.pot = pot;
        this.bear = bear;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(random.nextInt(900) + 100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.print(id + ": ");
            pot.add();

            if (pot.isFull()) {
                System.out.print("Bee " + id + ": ");
                bear.wakeUpBear();
            }
        }
    }

}

class Pot {
    private final int volume;
    static int filled = 0;

    public Pot(int volume) {
        this.volume = volume;
    }

    public synchronized void add(){
        while(isFull()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        filled++;
        System.out.println("Pot is filled for " + filled + "/" + volume);
    }

    synchronized boolean isFull() {
        return volume == filled;
    }

    synchronized void eat() {
        System.out.println("Winnie has eaten all honey");
        filled = 0;
        notifyAll();
    }
}

public class taskA {
    public static void main(String[] args) {
        int beeNumber = 4;
        Pot pot = new Pot(10);
        Bear bear = new Bear(pot);
        new Thread(bear).start();

        ExecutorService executors = Executors.newFixedThreadPool(beeNumber);
        for (int i = 0; i < beeNumber; i++) {
            Runnable bee = new Bee(pot,bear);
            executors.execute(bee);
        }

        executors.shutdown();
    }
}
