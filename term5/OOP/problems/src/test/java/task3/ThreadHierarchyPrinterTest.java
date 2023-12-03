package task3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ThreadHierarchyPrinterTest {

    @Test
    public void testThreadHierarchyPrinter() {
        ThreadGroup group1 = new ThreadGroup("Group1");
        ThreadGroup group2 = new ThreadGroup("Group2");
        ThreadGroup subGroup1 = new ThreadGroup(group1,"Subgroup1");

        Thread thread1 = new Thread(group1, () -> {
            while (true) {
                /* doing something */
            }
        });
        Thread thread2 = new Thread(group2, () -> {
            while (true) {
                /* doing something */
            }
        });
        Thread thread3 = new Thread(subGroup1, () -> {
            while (true) {
                /* doing something */
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        ThreadHierarchyPrinter.printGroupHierarchy(group1);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread3.stop();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread1.stop();

        assertEquals(1, group1.activeCount());
        assertEquals(0, subGroup1.activeCount());

        assertEquals(1, group2.activeCount());
        thread2.stop();

        ThreadGroup[] threadGroups = new ThreadGroup[1];
        group1.enumerate(threadGroups);
        assertTrue(threadGroups[0].getName().equals("Subgroup1"));
    }
}
