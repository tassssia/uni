package task9;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PhaserTest {

    @Test
    void testPhaserInitialization() {
        Phaser phaser = new Phaser(5);
        assertEquals(0, phaser.getPhase());
        assertEquals(5, phaser.getPartiesAtStart());
        assertEquals(5, phaser.getPartiesAwait());
    }

    @Test
    void testAdd() {
        Phaser phaser = new Phaser(5);
        phaser.add();
        assertEquals(6, phaser.getPartiesAtStart());
        assertEquals(6, phaser.getPartiesAwait());
    }

    @Test
    void testArrive() throws InterruptedException {
        Phaser phaser = new Phaser(3);

        Thread t1 = new Thread(phaser::arriveEnd);
        Thread t2 = new Thread(phaser::arriveEnd);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        assertEquals(1, phaser.getPartiesAwait());
    }
}
