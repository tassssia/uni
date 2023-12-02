package task7;

public class CyclicBarrier {
    private final int parties;
    private int count = 0;

    public CyclicBarrier(int parties) {
        this.parties = parties;
    }

    public synchronized void await() throws InterruptedException {
        count++;

        if (count < parties) {
            wait();
        } else {
            notifyAll();
            count = 0;
        }
    }

    public synchronized void reset() {
        count = 0;
        notifyAll();
    }
}

