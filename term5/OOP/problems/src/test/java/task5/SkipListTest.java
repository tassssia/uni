package task5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SkipListTest {

    @Test
    public void testAdd() {
        SkipList skipList = new SkipList(4);

        assertTrue(skipList.add(10));
        assertTrue(skipList.add(20));
        assertTrue(skipList.add(15));
        assertFalse(skipList.add(10));

        assertEquals(3, skipList.getSize().get());
        assertTrue(skipList.find(10));
        assertTrue(skipList.find(20));
        assertTrue(skipList.find(15));
        assertFalse(skipList.find(25));
    }

    @Test
    public void testRemove() {
        SkipList skipList = new SkipList(4);

        skipList.add(10);
        skipList.add(20);
        skipList.add(15);

        assertTrue(skipList.remove(20));
        assertFalse(skipList.remove(25));

        assertEquals(2, skipList.getSize().get());
        assertTrue(skipList.find(10));
        assertFalse(skipList.find(20));
        assertTrue(skipList.find(15));
    }

    @Test
    public void testFind() {
        SkipList skipList = new SkipList(4);

        skipList.add(10);
        skipList.add(20);
        skipList.add(15);

        assertTrue(skipList.find(10));
        assertTrue(skipList.find(20));
        assertTrue(skipList.find(15));
        assertFalse(skipList.find(25));
    }
}
