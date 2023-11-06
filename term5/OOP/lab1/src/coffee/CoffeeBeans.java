package coffee;

public class CoffeeBeans extends Coffee {
    public CoffeeBeans(double cost, int volume) {
        super(cost, volume);
        if (volume < 4) {
            setVolume(4);
        }
    }

    @Override
    public void prepareForSelling() {
        System.out.println("Packing a jar of coffee beans of weight " + weight + "...");
    }
}
