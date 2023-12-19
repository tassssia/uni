package socket;

import data.Developer;
import data.SoftwareProduct;
import database.SoftwareDB;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public class Services {
    private SoftwareDB db;
    private StringWriter result;

    public Services() {
        try {
            db = new SoftwareDB("software", "localhost", 3306);
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        result = new StringWriter();
    }

    public String getAllDevelopers() {
        ArrayList<Developer> developers = db.getAllDevelopers();

        result.append("ALL DEVELOPERS:#");
        for (Developer d : developers){
            result.append(d.toString() + "#");
        }
        return result.toString();
    }
    public String getAllProductsOfDeveloper(int id) {
        ArrayList<SoftwareProduct> products = db.getAllProductsOfDeveloper(id);

        result.append("ALL PRODUCTS OF DEVELOPER " + id + ":#");
        for (SoftwareProduct p :products) {
            result.append(p.toString() + "#");
        }
        return result.toString();
    }
    public String getAllProductsByName(String name) {
        ArrayList<SoftwareProduct> products = db.getAllProductsByName(name);

        result.append("ALL PRODUCTS WITH A NAME " + name + ":#");
        for (SoftwareProduct p :products) {
            result.append(p.toString() + "#");
        }
        return result.toString();
    }
    public String countAllProductsOfDeveloper(int id) {
        int count = db.countAllProductsOfDeveloper(id);

        result.append("DEVELOPER " + String.valueOf(id) + " HAS " + String.valueOf(count) + " PRODUCTS#");
        return result.toString();
    }

    public String addDeveloper(int id, String name, String founder, int year) {
        try {
            db.addDeveloper(id, name, founder, year);
            result.append("Developer " + String.valueOf(id) + " successfully added#");
        } catch (Exception e) {
            result.append("Failed to add developer " + String.valueOf(id) + "#");
        }
        return result.toString();
    }
    public String addProduct(int id, String name, int cost, int developer) {
        try {
            db.addProduct(id, name, cost, developer);
            result.append("Software product " + String.valueOf(id) + " successfully added#");
        } catch (Exception e) {
            result.append("Failed to add product " + String.valueOf(id) + "#");
        }
        return result.toString();
    }

    public String updateDeveloper(int id, String name) {
        try {
            db.updateDeveloper(id, name);
            result.append("Developer " + String.valueOf(id) + " successfully updated#");
        } catch (Exception e) {
            result.append("Failed to update developer " + String.valueOf(id) + "#");
        }
        return result.toString();
    }
    public String updateProduct(int id, String name, int cost) {
        try {
            db.updateProduct(id, name, cost);
            result.append("Software product " + String.valueOf(id) + " successfully updated#");
        } catch (Exception e) {
            result.append("Failed to update product " + String.valueOf(id) + "#");
        }
        return result.toString();
    }

    public String deleteDeveloper(int id) {
        try {
            db.deleteDeveloper(id);
            result.append("Developer " + String.valueOf(id) + " successfully deleted#");
        } catch (Exception e) {
            result.append("Failed to delete developer " + String.valueOf(id) + "#");
        }
        return result.toString();
    }
    public String deleteProduct(int id) {
        try {
            db.deleteProduct(id);
            result.append("Software product " + String.valueOf(id) + " successfully deleted#");
        } catch (Exception e) {
            result.append("Failed to delete product " + String.valueOf(id) + "#");
        }
        return result.toString();
    }
}