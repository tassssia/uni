import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;
import java.security.SecureRandom;

class Barber {
    public final Semaphore semaphore = new Semaphore(1);
    private final Queue<Visitor> visitors = new ConcurrentLinkedDeque<>();
    private static SecureRandom random = new SecureRandom();

    public void visitorHasCome(Visitor visitor) {
        visitors.add(visitor);
        System.out.println(Thread.currentThread().getName() + " is in line");
        cutHair();
    }

    public void cutHair() {
        try {
            visitors.remove();
            semaphore.acquire();
            System.out.println("Barber is cutting " + Thread.currentThread().getName());
            Thread.sleep(random.nextInt(1500) + 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Visitor implements Runnable {
    private final Barber barber;
    private int impression;
    private static SecureRandom random = new SecureRandom();

    public Visitor(Barber barber) {
        this.barber = barber;
    }

    @Override
    public void run() {
        barber.visitorHasCome(this);
        impression = random.nextInt(10) + 1;
        System.out.println(Thread.currentThread().getName() + " left " + (impression > 6 ? "happy" : "not satisfied"));
        barber.semaphore.release();
    }
}

public class taskB {
    public static void main(String[] args) {
        int numberOfVisitors = 10;
        Thread[] threads = new Thread[numberOfVisitors];
        Barber barber = new Barber();

        for (int i = 0; i < numberOfVisitors; i++) {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threads[i] = new Thread(new Visitor(barber), "Visitor " + i);
            threads[i].start();
        }

        for (int i = 0; i < numberOfVisitors; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
