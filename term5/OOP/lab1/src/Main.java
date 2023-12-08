import coffee.Coffee;

import java.security.SecureRandom;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();

        CoffeeVan van = new CoffeeVan(100);
        van.load(90);

        // instant coffee in a cup
        List<Coffee> instantCup = van.findByParameters("i", 4, 10, 1, 3);
        if(!instantCup.isEmpty()) {
            van.sell(instantCup.get(random.nextInt(instantCup.size())));
        }
        System.out.println();

        // instant coffee in a pack
        List<Coffee> instantPack = van.findByParameters("i", 4, 10, 4, 10);
        if(!instantPack.isEmpty()) {
            van.sell(instantPack.get(random.nextInt(instantPack.size())));
        }
        System.out.println();

        // coffee beans
        List<Coffee> beans = van.findByParameters("b", 14, 24, 5, 8);
        if(!beans.isEmpty()) {
            van.sell(beans.get(random.nextInt(beans.size())));
        }
        System.out.println();

        // ground coffee
        List<Coffee> ground = van.findByParameters("g", 14, 24, 2, 2);
        if(!ground.isEmpty()) {
            van.sell(ground.get(random.nextInt(ground.size())));
        }

        System.out.println("\n===================================");
        System.out.println("Cash: " + van.getCash());
        System.out.println("Tips: " + van.getTips());
    }
}
