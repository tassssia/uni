package coffee;

public abstract class Coffee {
    private static double COST_PER_X_WEIGHT = 100;
    private static double WEIGNT_PER_SERVING = 50;
    protected double price;
    protected double cost;
    protected double weight;
    protected int volume;

    protected Coffee(double cost, int volume) {
        setVolume(volume);
        setCost(cost);
    }


    public abstract void prepareForSelling();


    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public int getVolume() {
        return volume;
    }
    public void setVolume(int volume) {
        this.volume = volume;
        this.weight = WEIGNT_PER_SERVING * volume;
    }

    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
        this.price = cost * (weight / COST_PER_X_WEIGHT);
    }
}
