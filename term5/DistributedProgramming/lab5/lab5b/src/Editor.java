import java.security.SecureRandom;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Editor implements Runnable {
    private final SecureRandom random = new SecureRandom();
    private String line;
    private final CyclicBarrier barrier;
    private final Checker checker;
    private boolean running = true;
    private int ABnum;
    private final int id;

    public Editor(String line, CyclicBarrier barrier, Checker checker, int id){
        this.line = line;
        this.barrier = barrier;
        this.checker = checker;
        this.ABnum = countAB(line);
        this.id = id;
    }

    @Override
    public void run(){
        while(running) {
            int index = random.nextInt(line.length());

            switch (line.charAt(index)) {
                case 'A': {
                    line = line.substring(0, index) + 'C' + line.substring(index + 1);
                    ABnum--;
                    break;
                }
                case 'B': {
                    line = line.substring(0, index) + 'D' + line.substring(index + 1);
                    ABnum--;
                    break;
                }
                case 'C': {
                    line = line.substring(0, index) + 'A' + line.substring(index + 1);
                    ABnum++;
                    break;
                }
                case 'D': {
                    line = line.substring(0, index) + 'B' + line.substring(index + 1);
                    ABnum++;
                    break;
                }
            }

            checker.getInfo(ABnum);
            System.out.println(this.id + ": " + line + " (" + ABnum + ")");
            try {
                barrier.await();
            }
            catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

            if(this.id == 3) {
                System.out.println();
            }
            running = checker.isRunning();
        }
    }

    private int countAB(String str) {
        int count = 0;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == 'A' || str.charAt(i) == 'B'){
                count++;
            }
        }
        return count;
    }
}