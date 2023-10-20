import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class RecruitBlock implements Runnable {
    private static final AtomicBoolean done = new AtomicBoolean(false);
    private static final List<Boolean> doneStatus = new ArrayList<>();

    private final char[] recruits;
    private final Barrier barrier;

    private final int blockId;
    private final int left;
    private final int right;

    public RecruitBlock(char[] recruits, Barrier barrier, int blockId, int left, int right) {
        this.recruits = recruits;
        this.barrier = barrier;
        this.blockId = blockId;
        this.left = left;
        this.right = right;
    }

    public void run() {
        while (!done.get()) {
            boolean isCurrDone = doneStatus.get(blockId);
            
            if (!isCurrDone) {
                print();
                boolean isCorrect = true;
                
                for (int i = left; i < right - 1; i++) {
                    if (recruits[i] != recruits[i+1]) {
                        if (recruits[i] == '>') {
                            recruits[i] = '<';
                        }
                        else {
                            recruits[i] = '<';
                        }
                        isCorrect = false;
                    }
                }
                
                if(isCorrect) { finish(); }
            }
            
            try { 
                barrier.await();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void finish() {
        doneStatus.set(blockId, true);
        for (boolean block: doneStatus) {
            if (!block) return;
        }
        done.set(true);
    }

    public static void fillDoneStatus(int numBlocks) {
        for (int i = 0; i < numBlocks; i++) {
            doneStatus.add(false);
        }
    }

    public void print() {
        String res = blockId + ": ";
        for (int i = left; i < right; i++) {
            res += recruits[i];
        }
        System.out.println(res);
    }
}