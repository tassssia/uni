package task6;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NonBlockingQueueTest {

    @Test
    public void testEnqueueDequeue() {
        NonBlockingQueue<Integer> queue = new NonBlockingQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);

        assertEquals(Integer.valueOf(1), queue.dequeue());
        assertEquals(Integer.valueOf(2), queue.dequeue());
        assertNull(queue.dequeue());
    }

    @Test
    public void testConcurrentEnqueueDequeue() throws InterruptedException {
        final NonBlockingQueue<Integer> queue = new NonBlockingQueue<>();
        final int numOperations = 1000;
        final int numThreads = 10;
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            final int threadNum = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < numOperations; j++) {
                    if (j % 2 == 0) {
                        queue.enqueue(threadNum * numOperations + j);
                    } else {
                        queue.dequeue();
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        assertNull(queue.dequeue());
    }
}
