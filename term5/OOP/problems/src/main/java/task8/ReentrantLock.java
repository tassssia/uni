package task8;

public class ReentrantLock {
    private boolean lockedStatus = false;
    private Thread lockedBy = null;
    private int lockCount = 0;

    public synchronized void lock() throws InterruptedException {
        Thread callingThread = Thread.currentThread();
        while (lockedStatus && lockedBy != callingThread) {
            wait();
        }
        lockedStatus = true;
        lockedBy = callingThread;
        lockCount++;
    }

    public synchronized void unlock() {
        if (Thread.currentThread() == lockedBy) {
            lockCount--;

            if (lockCount == 0) {
                lockedStatus = false;
                notify();
                lockedBy = null;
            }
        }
    }

    public boolean isLocked() {
        return lockedStatus;
    }
}
