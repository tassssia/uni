package data;

import java.util.ArrayList;

public class Software {
    private ArrayList<Developer> developers;
    private ArrayList<SoftwareProduct> products;

    public Software() {
        developers = new ArrayList<>();
        products = new ArrayList<>();
    }

    public ArrayList<Developer> getDevelopers(){
        return developers;
    }
    public ArrayList<SoftwareProduct> getProducts(){
        return products;
    }
    public Developer getDeveloper(int id) {
        for (Developer d : developers) {
            if (d.getId() == id) {
                return d;
            }
        }

        return null;
    }
    public SoftwareProduct getProduct(int id) {
        for (SoftwareProduct p : products) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }

    public void addDeveloper(int id, String name, String founder, int year) {
        for (Developer d : developers) {
            if (d.getId() == id) {
                System.out.println("Such ID already exists");
                return;
            }
        }

        developers.add(new Developer(id, name, founder, year));
    }
    public void addDeveloper(Developer developer) {
        for (Developer d : developers) {
            if (d.getId() == developer.getId()) {
                System.out.println("Such ID already exists");
                return;
            }
        }

        developers.add(new Developer(developer.getId(), developer.getName(),
                developer.getFounder(), developer.getYear()));
    }
    public void addProduct(int id, String name, int cost, int developerId) {
        for (SoftwareProduct p : products) {
            if (p.getId() == id) {
                System.out.println("Such ID already exists");
                return;
            }
        }

        for (Developer d : developers) {
            if (d.getId() == developerId) {
                products.add(new SoftwareProduct(id, name, cost, d));
                return;
            }
        }

        System.out.println("Developer's ID is not found");
    }
    public void addProduct(SoftwareProduct product) {
        for (SoftwareProduct p : products) {
            if (p.getId() == product.getId()) {
                System.out.println("Such ID already exists");
                return;
            }
        }

        for (Developer d : developers) {
            if (d.getId() == product.getDeveloper().getId()) {
                products.add(new SoftwareProduct(product.getId(), product.getName(), product.getCost(), d));
                return;
            }
        }

        System.out.println("Developer's ID is not found");
    }

    public void updateDeveloper(int id, String name) {
        Developer d = getDeveloper(id);
        if (d == null) {
            System.out.println("Developer's ID is not found");
            return;
        }

        d.setName(name);
    }
    public void updateProduct(int id, String name, int cost) {
        SoftwareProduct p = getProduct(id);
        if (p == null) {
            System.out.println("Product's ID is not found");
            return;
        }

        p.setName(name);
        p.setCost(cost);
    }

    public void deleteDeveloper(int id) {
        if (getDeveloper(id) == null) {
            System.out.println("Developer's ID is not found");
            return;
        }

        for (Developer d : developers) {
            if (d.getId() == id) {
                products.removeIf(p -> p.getDeveloper().getId() == id);
                developers.remove(d);
                return;
            }
        }
    }
    public void deleteProduct(int id) {
        for (SoftwareProduct p : products) {
            if (p.getId() == id) {
                products.remove(p);
                return;
            }
        }

        System.out.println("Product's ID is not found");
    }

    public void printDevelopers() {
        for (Developer d : developers) {
            System.out.println(d.getName());
        }
    }
    public void printProductsOfDeveloper(int id) {
        for (SoftwareProduct p : products){
            if (p.getDeveloper().getId() == id) {
                System.out.println(p.getName());
            }
        }
    }
}
