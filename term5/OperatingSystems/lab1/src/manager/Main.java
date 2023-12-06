package manager;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int arg = 0;
        boolean isInt = false;
        while (!isInt) {
            System.out.println("x = ");
            if (scanner.hasNextInt()) {
                arg = scanner.nextInt();
                isInt = true;
            }
            scanner.nextLine();
        }

        try {
            long tStart = System.currentTimeMillis();
            Manager manager = new Manager(arg);
            long tEnd = System.currentTimeMillis();
            System.out.println("Completed.");

            int fStatus = manager.getStatusF();
            int gStatus = manager.getStatusG();
            Boolean cancelStatus = manager.isCanceled;

            if(cancelStatus) {
                System.out.println("Computation cancelled");
            }
            if(fStatus + gStatus > 0) {
                System.out.println("Result: fail");
            }
            else if(fStatus == 0 && gStatus == 0) {
                System.out.println("Result: " + manager.getResult());
            }
            else {
                System.out.println("Result: undetermined");
            }

            System.out.println("Computation took " + (tEnd - tStart) + " ms");

            manager.destroyProcs();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}