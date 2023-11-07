import coffee.*;

import java.util.ArrayList;
import java.security.SecureRandom;

public class CoffeeVan {
    SecureRandom random = new SecureRandom();
    protected ArrayList<Coffee> cargo;
    protected int capacity;
    protected int loaded;
    private double cash;
    private double tips;

    public CoffeeVan(int capacity) {
        cargo = new ArrayList<>();
        this.capacity = capacity;
        loaded = 0;
        cash = 0;
        tips = 0;
    }

    public void load(int capToLoad) {
        boolean flag = true;
        while (loaded < capToLoad && flag) {
            int type = random.nextInt(3);

            switch (type) {
                case 0:
                    flag = addCoffeeItem(new CoffeeBeans(16 + 2 * random.nextInt(2),
                            random.nextInt(7) + 4));
                    break;
                case 1:
                    flag = addCoffeeItem(new InstantCoffee(8,
                            random.nextInt(10) + 1));
                    break;
                case 2:
                    flag = addCoffeeItem(new GroundCoffee(18 + 2 * random.nextInt(3),
                            random.nextInt(3) + 1));
                    break;
            }
        }
    }
    public boolean load(ArrayList<Coffee> toLoad) {
        for (Coffee coffeeToLoad : toLoad) {
            if(!addCoffeeItem(coffeeToLoad)) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<Coffee> findByParameters(String type, double minCost, double maxCost, int minVol, int maxVol) {
        ArrayList<Coffee> res = new ArrayList<>();
        type = type.toLowerCase();

        int len = cargo.size();
        int minCostInd = 0;
        while (minCostInd < len && cargo.get(minCostInd).getCost() < minCost) {
            minCostInd++;
        }
        int maxCostInd = len - 1;
        while (maxCostInd >= 0 && cargo.get(maxCostInd).getCost() > maxCost) {
            maxCostInd--;
        }

        for (int i = minCostInd; i <= maxCostInd; i++) {
            Coffee coffee = cargo.get(i);

            if (coffee.getClass().getName().toLowerCase().contains(type)
                    && coffee.getVolume() >= minVol && coffee.getVolume() <= maxVol) {
                res.add(coffee);
            }
        }

        return res;
    }

    private void insertSorted(Coffee toIns) {
        int len = cargo.size();
        int ind = 0;
        while (ind < len && cargo.get(ind).getCost() < toIns.getCost()) {
            ind++;
        }

        cargo.add(ind, toIns);
    }

    public boolean addCoffeeItem(Coffee toAdd) {
        if (loaded + toAdd.getVolume() <= capacity) {
            insertSorted(toAdd);
            loaded += toAdd.getVolume();
            return true;
        } else {
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
                * Math.signum((double) random.nextInt(5));
    }

    public double getCash() {
        return Math.round(cash * 100.0) / 100.0;
    }
    public double getTips() {
        return Math.round(tips * 100.0) / 100.0;
    }
}
