package coffee;

import java.util.Scanner;

public class GroundCoffee extends Coffee {
    public GroundCoffee(double cost, int volume) {
        super(cost, volume);
        if (volume > 3) {
            setVolume(3);
        }
    }

    @Override
    public void prepareForSelling() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which brewing do you prefer: espresso, filter, or Turkish? [e/f/t] ");
        String brewingType = scanner.nextLine().toLowerCase();
        scanner.close();

        brew(brewingType);
    }

    private void brew(String type) {
        switch (type) {
            case "e":
                System.out.println("Brewing espresso...");
                break;
            case "f":
                System.out.println("Brewing filter coffee...");
                break;
            case "t":
                System.out.println("Brewing Turkish coffee...");
                break;
            default:
                System.out.println("Brewing something improvised...");
                break;
        }
    }

}
