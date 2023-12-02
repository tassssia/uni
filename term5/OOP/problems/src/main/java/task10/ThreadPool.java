package task10;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final int threadCount;
    private final BlockingQueue<Runnable> taskQueue;
    private final WorkerThread[] workerThreads;

    public ThreadPool(int threadCount) {
        this.threadCount = threadCount;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.workerThreads = new WorkerThread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            workerThreads[i] = new WorkerThread();
            workerThreads[i].start();
        }
    }

    public void submit(Runnable task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        for (WorkerThread workerThread : workerThreads) {
            workerThread.interrupt();
        }
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
