package coffee;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoffeeTest {

    @Test
    public void testSetVolume() {
        Coffee coffee = new InstantCoffee(5.0, 4);
        coffee.setVolume(2);
        assertEquals(2, coffee.getVolume());
        assertEquals(100.0, coffee.getWeight());
    }

    @Test
    public void testSetCost() {
        Coffee coffee = new InstantCoffee(5.0, 4);
        coffee.setCost(6.0);
        assertEquals(6.0, coffee.getCost());
        assertEquals(12.0, coffee.getPrice());
    }

    @Test
    public void testGetPrice() {
        Coffee coffee = new InstantCoffee(5.0, 4);
        assertEquals(10.0, coffee.getPrice());
    }

    @Test
    public void testGetWeight() {
        Coffee coffee = new InstantCoffee(5.0, 4);
        assertEquals(200.0, coffee.getWeight());
    }

    @Test
    public void testGetVolume() {
        Coffee coffee = new InstantCoffee(5.0, 4);
        assertEquals(4, coffee.getVolume());
    }

    @Test
    public void testConstructor() {
        Coffee coffee = new InstantCoffee(5.0, 4);
        assertEquals(5.0, coffee.getCost());
        assertEquals(4, coffee.getVolume());
        assertEquals(200.0, coffee.getWeight());
    }
}
