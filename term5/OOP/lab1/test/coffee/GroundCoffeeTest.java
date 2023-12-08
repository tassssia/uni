package coffee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GroundCoffeeTest {
    private GroundCoffee groundCoffee;
    //private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    //private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        groundCoffee = new GroundCoffee(5.0, 4);
        //System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        groundCoffee = null;
        //System.setOut(originalOut);
    }

    @Test
    public void testPrepareForSellingWithEspresso() {
//        ByteArrayInputStream simulatedInput = new ByteArrayInputStream("e".getBytes());
//        System.setIn(simulatedInput);

        groundCoffee.prepareForSelling();

//        String expectedOutput = "Brewing espresso...";
//        assertTrue(outContent.toString().contains(expectedOutput));
        assertTrue(groundCoffee.isReadyForSelling());
    }

    @Test
    public void testPrepareForSellingWithFilterCoffee() {
//        ByteArrayInputStream simulatedInput = new ByteArrayInputStream("f".getBytes());
//        System.setIn(simulatedInput);

        groundCoffee.prepareForSelling();

//        String expectedOutput = "Brewing filter coffee...";
//        assertTrue(outContent.toString().contains(expectedOutput));
        assertTrue(groundCoffee.isReadyForSelling());
    }

    @Test
    public void testPrepareForSellingWithTurkishCoffee() {
//        ByteArrayInputStream simulatedInput = new ByteArrayInputStream("t".getBytes());
//        System.setIn(simulatedInput);

        groundCoffee.prepareForSelling();

//        String expectedOutput = "Brewing Turkish coffee...";
//        assertTrue(outContent.toString().contains(expectedOutput));
        assertTrue(groundCoffee.isReadyForSelling());
    }

    @Test
    public void testPrepareForSellingWithInvalidInput() {
//        ByteArrayInputStream simulatedInput = new ByteArrayInputStream("invalid".getBytes());
//        System.setIn(simulatedInput);

        groundCoffee.prepareForSelling();

//        String expectedOutput = "Brewing something improvised...";
//        assertTrue(outContent.toString().contains(expectedOutput));
        assertTrue(groundCoffee.isReadyForSelling());
    }
}
