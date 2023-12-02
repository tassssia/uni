package task9;

public class Phaser {
    private int phase;
    private int partiesAtStart;
    private int partiesAwait;


    public Phaser(int partiesAtStart) {
        this.partiesAtStart = partiesAtStart;
        this.partiesAwait = partiesAtStart;
        this.phase = 0;
    }

    public synchronized void add() {
        partiesAtStart++;
        partiesAwait++;
    }

    public synchronized void arriveContinue() {
        partiesAwait++;
        while (partiesAwait > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(partiesAwait == 0) {
            reset();
        }
    }

    public synchronized void arriveEnd() {
        partiesAwait--;
        partiesAtStart--;
        if(partiesAwait == 0) {
            reset();
        }
    }

    private void reset() {
        partiesAwait = partiesAtStart;
        phase++;
        notifyAll();
    }

    public int getPhase() {
        return phase;
    }

    public int getPartiesAtStart() {
        return partiesAtStart;
    }
    public int getPartiesAwait() {
        return partiesAwait;
    }
}
