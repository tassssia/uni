import java.util.Queue;
import java.util.LinkedList;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class taskA {
    private int swarmsNum;
    private int forestSize;
    private int sectionMaxSize;
    private int sectionsNum;
    private ArrayList<Boolean> forest = new ArrayList<Boolean>();
    public Queue<Integer> taskQueue = new LinkedList<>();

    public taskA(int swarmsNum, int forestSize, int sectionMaxSize) {
        this.swarmsNum = swarmsNum;
        this.forestSize = forestSize;
        this.sectionMaxSize = sectionMaxSize;

        this.sectionsNum = forestSize / sectionMaxSize + Math.round(Math.signum(forestSize % sectionMaxSize));

        for (int i = 0; i < forestSize; i++) {
            forest.add(false);
        }
        SecureRandom random = new SecureRandom();
        int winnie = random.nextInt(forestSize);
        forest.set(winnie, true);
        System.out.println("Winnie has hidden in cell " + winnie);

        for (int i = 0; i < sectionsNum; i++) {
            taskQueue.offer(i);
        }
    }

    public void searching() {
        ExecutorService executor = Executors.newFixedThreadPool(swarmsNum);
        for (int i = 0; i < swarmsNum; i++) {
            executor.execute(new Swarm(i, this.taskQueue));
        }
        executor.shutdown();
    }

    class Swarm implements Runnable {
        private final int id;
        private final Queue<Integer> taskQueue;

        public Swarm(int id, Queue<Integer> taskQueue) {
            this.id = id;
            this.taskQueue = taskQueue;
        }

        @Override
        public void run() {
            while (true) {
                Integer task = getNextTask();
                if (task == null) {
                    System.out.println("Swarm " + id + " has finished searching");
                    return;
                }
                System.out.println("Swarm " + id + " is searching in section " + task);
                for (int i = task * sectionMaxSize; i < (task+1) * sectionMaxSize && i < forestSize; i++) {
                    if(forest.get(i)) {
                        System.out.println("Swarm " + id + " found Winnie the Pooh in section " + task + "!");
                        return;
                    }
                }
            }
        }

        private Integer getNextTask() {
            synchronized (taskQueue) {
                if (!taskQueue.isEmpty()) {
                    return taskQueue.poll();
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        int swarmsNum = 10;
        int forestSize = 1000;
        int sectionMaxSize = 37;

        taskA a = new taskA(swarmsNum, forestSize, sectionMaxSize);
        a.searching();
    }
}