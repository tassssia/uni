package task3;

public class ThreadHierarchyPrinter {

    public static void printGroupHierarchy(ThreadGroup threadGroup) {
        Thread printerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Thread Hierarchy for Group: " + threadGroup.getName());
                printThreadGroupHierarchy(threadGroup, "");
                System.out.println("---------------------------------");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        printerThread.start();
    }

    private static void printThreadGroupHierarchy(ThreadGroup group, String indent) {
        System.out.println(indent + "Group: " + group.getName());
        int numOfThreads = group.activeCount();
        Thread[] threads = new Thread[numOfThreads];
        numOfThreads = group.enumerate(threads, false);

        for (int i = 0; i < numOfThreads; i++) {
            System.out.println(indent + "  |- Thread: " + threads[i].getName());
        }

        int numOfGroups = group.activeGroupCount();
        ThreadGroup[] groups = new ThreadGroup[numOfGroups];
        numOfGroups = group.enumerate(groups, false);

        for (int i = 0; i < numOfGroups; i++) {
            printThreadGroupHierarchy(groups[i], indent + "  ");
        }
    }
}

