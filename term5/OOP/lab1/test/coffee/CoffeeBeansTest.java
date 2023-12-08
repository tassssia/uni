package coffee;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class CoffeeBeansTest {

    @Test
    public void testConstructorWithVolumeLessThan4() {
        CoffeeBeans coffeeBeans = new CoffeeBeans(5.0, 3);
        assertEquals(4, coffeeBeans.getVolume());
    }

    @Test
    public void testConstructorWithVolumeGreaterThan4() {
        CoffeeBeans coffeeBeans = new CoffeeBeans(5.0, 6);
        assertEquals(6, coffeeBeans.getVolume());
    }

    @Test
    public void testPrepareForSelling() {
        CoffeeBeans coffeeBeans = new CoffeeBeans(5.0, 4);
//        String expectedOutput = "Packing a jar of coffee beans of weight " + coffeeBeans.getWeight() + "...";
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
        coffeeBeans.prepareForSelling();
//        assertEquals(expectedOutput, outContent.toString().trim());
        assertTrue(coffeeBeans.isReadyForSelling());
    }
}
