package data;

public class Developer {
    private final int id;
    private String name;
    private String founder;
    private final int year; // year founded

    public Developer(int id, String name, String founder, int year) {
        this.id = id;
        this.name = name;
        this.founder = founder;
        this.year = year;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getFounder() {
        return founder;
    }
    public int getYear() {
        return year;
    }

    public void setName(String name) {
        this.name = name;
    }
}
