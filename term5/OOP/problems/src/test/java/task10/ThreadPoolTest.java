package task10;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ThreadPoolTest {

    @Test
    public void testThreadPool() {
        ThreadPool threadPool = new ThreadPool(3);
        final int[] counter = {0};

        for (int i = 0; i < 10; i++) {
            threadPool.submit(() -> {
                System.out.println("Task is running in thread: " + Thread.currentThread().getName());
                counter[0]++;
            });
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        threadPool.shutdown();

        assertEquals(10, counter[0]);
    }
}
