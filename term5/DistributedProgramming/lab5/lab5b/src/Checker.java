import java.util.Arrays;

public class Checker {
    private boolean isRunning = true;
    private int threadsCounter = 0;
    private final int threadsTotal;
    private final int[] ABs;
    private boolean aBoolean = false;

    public Checker(int threadNum) {
        threadsTotal = threadNum;
        ABs = new int[threadNum];
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void equalityCheck() {
        boolean isEqual = true;
        Arrays.sort(ABs);

        for (int i = 1; i < ABs.length - 2; i++) {
            if (ABs[i] != ABs[i + 1]) {
                isEqual = false;
                break;
            }
        }
        if (isEqual) {
            if (ABs[0] == ABs[1] || ABs[ABs.length - 1] == ABs[1]) {
                isRunning = false;
                System.out.println("Became equal");
            }
        }
    }

    public synchronized void getInfo(int data) {
        ABs[threadsCounter] = data;
        threadsCounter++;
        if (threadsCounter == threadsTotal) {
            notifyAll();
            aBoolean = true;
        }
        while (!aBoolean) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threadsCounter--;
        if (threadsCounter == 0) {
            equalityCheck();
            aBoolean = false;
        }
    }
}