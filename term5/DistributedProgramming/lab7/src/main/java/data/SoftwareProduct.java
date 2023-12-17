package data;

public class SoftwareProduct {
    private final int id;
    private String name;
    private int cost;
    private final Developer developer;

    public SoftwareProduct(int id, String name, int cost, Developer developer) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.developer = developer;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getCost() {
        return cost;
    }
    public Developer getDeveloper() {
        return developer;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
}
