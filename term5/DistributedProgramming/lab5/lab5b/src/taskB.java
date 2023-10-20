import java.util.concurrent.CyclicBarrier;

public class taskB {
    private static final int numOfThreads = 4;

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(numOfThreads);
        Checker checker = new Checker(numOfThreads);

        Thread editor1 = new Thread(new Editor("ABCDCDAABCD", barrier, checker, 0));
        Thread editor2 = new Thread(new Editor("AAACAACBBAC", barrier, checker, 1));
        Thread editor3 = new Thread(new Editor("ACDCADCACDC", barrier, checker, 2));
        Thread editor4 = new Thread(new Editor("CDABBABCDAB", barrier, checker, 3));

        editor1.start();
        editor2.start();
        editor3.start();
        editor4.start();
    }
}