package task8;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReentrantLockTest {
    @Test
    public void testLockUnlock() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();

        reentrantLock.lock();
        assertTrue(reentrantLock.isLocked());

        reentrantLock.unlock();
        assertFalse(reentrantLock.isLocked());
    }

    @Test
    public void testReentrantLock() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();

        reentrantLock.lock();
        assertTrue(reentrantLock.isLocked());

        reentrantLock.lock();
        assertTrue(reentrantLock.isLocked());

        reentrantLock.unlock();
        assertTrue(reentrantLock.isLocked());

        reentrantLock.unlock();
        assertFalse(reentrantLock.isLocked());
    }

    @Test
    public void testMultipleThreads() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();

        Thread thread1 = new Thread(() -> {
            try {
                reentrantLock.lock();
                Thread.sleep(1000);
                reentrantLock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(500);
                reentrantLock.lock();
                reentrantLock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertFalse(reentrantLock.isLocked());
    }
}
