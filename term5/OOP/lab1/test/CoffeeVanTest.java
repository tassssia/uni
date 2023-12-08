import static org.junit.jupiter.api.Assertions.*;

import coffee.Coffee;
import coffee.CoffeeBeans;
import coffee.GroundCoffee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class CoffeeVanTest {
    private CoffeeVan coffeeVan;

    @BeforeEach
    public void setUp() {
        coffeeVan = new CoffeeVan(1000);
    }

    @Test
    public void testLoad() {
        coffeeVan.load(500);
        assertTrue(coffeeVan.getLoaded() > 0);
    }

    @Test
    public void testLoadWithList() {
        ArrayList<Coffee> coffeeList = new ArrayList<>();
        coffeeList.add(new CoffeeBeans(20, 5));
        coffeeList.add(new GroundCoffee(22, 3));

        assertTrue(coffeeVan.load(coffeeList));
        assertEquals(2, coffeeVan.getCargo().size());
    }

    @Test
    public void testFindByParameters() {
        coffeeVan.load(500);

        List<Coffee> foundCoffee = coffeeVan.findByParameters("coffeebeans", 15, 25, 4, 6);
        for (Coffee coffee : foundCoffee) {
            assertTrue(coffee instanceof CoffeeBeans);
            assertTrue(coffee.getCost() >= 15 && coffee.getCost() <= 25);
            assertTrue(coffee.getVolume() >= 4 && coffee.getVolume() <= 6);
        }
    }

    @Test
    public void testSell() {
        coffeeVan.addCoffeeItem(new CoffeeBeans(20, 6));
        Coffee coffeeToSell = coffeeVan.getCargo().get(0);
        double initialCash = coffeeVan.getCash();

        coffeeVan.sell(coffeeToSell);

        assertTrue(coffeeVan.getCargo().isEmpty());
        assertEquals(initialCash + coffeeToSell.getPrice(), coffeeVan.getCash());
    }
}
