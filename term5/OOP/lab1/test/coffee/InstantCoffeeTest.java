package coffee;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class InstantCoffeeTest {

    @Test
    public void testPrepareForSellingWithVolumeLessThan4() {
        InstantCoffee instantCoffee = new InstantCoffee(5.0, 3);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instantCoffee.prepareForSelling();

        String expectedOutput = "Mixing instant coffee of size 3...";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    public void testPrepareForSellingWithVolumeGreaterOrEqualTo4() {
        InstantCoffee instantCoffee = new InstantCoffee(5.0, 4);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instantCoffee.prepareForSelling();

        String expectedOutput = "Packing a jar of instant coffee of weight " + instantCoffee.getWeight() + "...";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    public void testPrepareForSellingWithVolumeEqualTo4() {
        InstantCoffee instantCoffee = new InstantCoffee(5.0, 4);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instantCoffee.prepareForSelling();

        String expectedOutput = "Packing a jar of instant coffee of weight " + instantCoffee.getWeight() + "...";
        assertEquals(expectedOutput, outContent.toString().trim());
    }
}
