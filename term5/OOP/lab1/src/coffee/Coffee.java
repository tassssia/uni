package coffee;

public abstract class Coffee {
    protected double price;
    protected double cost; // per 100 of weight
    protected double weight;
    protected int volume; // 50 of weight per serving

    protected Coffee(double cost, int volume) {
        this.cost = cost;
        this.weight = 50 * volume;
        this.volume = volume;
        this.price = cost * weight * 0.01;
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
        this.weight = 100*volume;
    }

    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
        this.price = cost * weight * 0.01;
    }
}
