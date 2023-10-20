public class Barrier {
    private final int atStart;
    private int awaiting;

    public Barrier(int totalNum) {
        this.atStart = totalNum;
        this.awaiting = totalNum;
    }

    public synchronized void await() throws InterruptedException {
        awaiting--;
        if(awaiting > 0) {
            this.wait();
        }
        else {
            awaiting = atStart;
            notifyAll();
        }
    }
}