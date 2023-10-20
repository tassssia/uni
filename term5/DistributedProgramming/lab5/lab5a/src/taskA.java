import java.security.SecureRandom;

public class taskA {
    private static final SecureRandom random = new SecureRandom();
    private static final int numRecruits = 150;
    private static final int numBlocks = 3;
    private static final Thread[] threads = new Thread[numBlocks];
    private static final char[] recruits = new char[numRecruits];
    private static final Barrier BARRIER = new Barrier(numBlocks);

    public static void main(String[] args) {
        RecruitBlock.fillDoneStatus(numBlocks);

        for(int i = 0; i < numRecruits; i++) {
            if(random.nextBoolean()) recruits[i] = '>';
            else recruits[i] = '<';
        }

        threads[0] = new Thread(new RecruitBlock(recruits, BARRIER, 0,0, (numRecruits / numBlocks) + 1));
        for(int i = 1; i < numBlocks; i++) {
            threads[i] = new Thread(new RecruitBlock(recruits, BARRIER, i,
                    i * (numRecruits / numBlocks) - 1,
                    (i + 1) * (numRecruits / numBlocks) + 1));
        }
        threads[numBlocks - 1] = new Thread(new RecruitBlock(recruits, BARRIER, numBlocks - 1,
                (numBlocks - 1) * (numRecruits / numBlocks) - 1, numRecruits));

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Result: ");
        for (int i = 0; i < numRecruits; i++) {
            System.out.print(recruits[i]);
        }
        System.out.println();
    }
}