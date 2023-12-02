package task7;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CyclicBarrierTest {
    private static final int THREAD_COUNT = 3;

    @Test
    public void testCyclicBarrier() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_COUNT);

        TestThread[] threads = new TestThread[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new TestThread(cyclicBarrier);
            threads[i].start();
        }

        for (TestThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (TestThread thread : threads) {
            assertEquals(THREAD_COUNT, thread.getCount());
        }
    }

    private static class TestThread extends Thread {
        private final CyclicBarrier cyclicBarrier;
        private int count = 0;

        public TestThread(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 3; i++) {
                    cyclicBarrier.await();
                    count++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public int getCount() {
            return count;
        }
    }
}
