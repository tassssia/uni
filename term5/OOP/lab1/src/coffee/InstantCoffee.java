package coffee;

public class InstantCoffee extends Coffee {
    public InstantCoffee(double cost, int volume) {
        super(cost, volume);
    }

    private void addBoilingWater() {
        readyForSelling = true;
        //System.out.println("Mixing instant coffee of size " + volume + "...");
    }
    private void pack() {
        readyForSelling = true;
        //System.out.println("Packing a jar of instant coffee of weight " + weight + "...");
    }

    @Override
    public void prepareForSelling() {
        if (volume < 4) {
            addBoilingWater();
        }
        else {
            pack();
        }
    }
}
