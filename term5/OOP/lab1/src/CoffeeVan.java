import coffee.Coffee;
import java.util.ArrayList;
import java.security.SecureRandom;

public class CoffeeVan {
    SecureRandom random = new SecureRandom();
    private ArrayList<Coffee> cargo;
    private int capacity;
    private int loaded;
    private double cash;
    private double tips;

    public CoffeeVan(int capacity) {
        cargo = new ArrayList<>();
        loaded = 0;
        cash = 0;
        tips = 0;
        load(capacity);
    }

    private void load(int capacity) {
        // creating coffees and adding them to cargo
    }

    public boolean addCoffeeItem(Coffee toAdd) {
        if (loaded + toAdd.getVolume() <= capacity) {
            cargo.add(toAdd);
            loaded += toAdd.getVolume();
            return true;
        }
        else {
            System.out.println("Ooops, there is not enough space in the van");
            return false;
        }
    }

    public void sell(Coffee toSell) {
        toSell.prepareForSelling();

        cargo.remove(toSell);
        loaded -= toSell.getVolume();

        cash += toSell.getPrice();
        tips += ((random.nextInt(21) + 10) * toSell.getPrice() * 0.01)
                * Math.signum((double)random.nextInt(5));
    }
}
