package RMI;

import data.Developer;
import data.SoftwareProduct;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Client {
    private static SoftwareServer server;

    public static String getAllDevelopers() throws IOException {
        StringBuilder result = new StringBuilder();
        result.append("ALL DEVELOPERS:\n");

        ArrayList<Developer> developers = server.getAllDevelopers();
        for (Developer d : developers) {
            result.append(d.toString() + "\n");
        }
        return result.toString();
    }
    public static String getAllProductsOfDeveloper(String id) throws IOException {
        StringBuilder result = new StringBuilder();
        result.append("ALL PRODUCTS OF DEVELOPER " + id + ":\n");

        ArrayList<SoftwareProduct> products = server.getAllProductsOfDeveloper(Integer.parseInt(id));
        for (SoftwareProduct p : products) {
            result.append(p.toString() + "\n");
        }
        return result.toString();
    }
    public static String getAllProductsByName(String name) throws IOException {
        StringBuilder result = new StringBuilder();
        result.append("SOFTWARE PRODUCTS NAMED '"+ name +"': \n");

        ArrayList<SoftwareProduct> products = server.getAllProductsByName(name);
        for (SoftwareProduct p : products) {
            result.append(p.toString() + "\n");
        }
        return result.toString();
    }
    public static String countAllProductsOfDeveloper(String id) throws IOException {
        StringBuilder result = new StringBuilder();
        result.append(server.countAllProductsOfDeveloper(Integer.parseInt(id)) + " products of set developer" + id + "\n");
        return result.toString();
    }

    public static void addDeveloper(String id, String name, String founder, String year) throws IOException {
        server.addDeveloper(Integer.parseInt(id), name, founder, Integer.parseInt(year));
    }
    public static void addProduct(String id, String name, String cost, String developer) throws IOException {
        server.addProduct(Integer.parseInt(id), name, Integer.parseInt(cost), Integer.parseInt(developer));
    }

    public static void updateDeveloper( String id, String name) throws IOException {
        server.updateDeveloper(Integer.parseInt(id), name);
    }
    public static void updateProduct(String id, String name, String cost) throws IOException {
        server.updateProduct(Integer.parseInt(id), name, Integer.parseInt(cost));
    }

    public static void deleteDeveloper(String id) throws IOException {
        server.deleteDeveloper(Integer.parseInt(id));
    }
    public static void deleteProduct(String id) throws IOException {
        server.deleteProduct(Integer.parseInt(id));
    }

    public static void main(String[] args) throws NotBoundException, IOException {
        String url = "//localhost:123/software";
        server = (SoftwareServer) Naming.lookup(url);

        System.out.println("Connected to the server.");

        System.out.println(getAllDevelopers());
        System.out.println(getAllProductsOfDeveloper("1"));
        System.out.println(countAllProductsOfDeveloper("1"));

        addProduct("6", "name", "66", "1");
        System.out.println(getAllProductsOfDeveloper("1"));
        updateProduct("6", "another name", "666");
        System.out.println(getAllProductsOfDeveloper("1"));
        deleteProduct("6");
        System.out.println(getAllProductsOfDeveloper("1"));

        addDeveloper("6","test","test", "1111");
        System.out.println(getAllDevelopers());
        deleteDeveloper("6");
        System.out.println(getAllDevelopers());
    }
}
