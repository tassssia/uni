package task2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TridiagonalSystemSolver {
    public static double[] solve(TridiagonalSystem system) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < system.getSize()-1; i++) {
            final int step = i;
            executor.submit(new Callable<Void>() {
                @Override
                public Void call() {
                    system.forwardStep(step);
                    return null;
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = system.getSize()-1; i >= 0; i--) {
            final int step = i;
            executor.submit(new Callable<Void>() {
                @Override
                public Void call() {
                    system.backwardsStep(step);
                    return null;
                }
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return system.getY().clone();
    }
}
