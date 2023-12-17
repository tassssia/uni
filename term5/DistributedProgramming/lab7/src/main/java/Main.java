import data.Software;

import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws TransformerException, SAXException {
        Parser parser = new Parser();
        Software software = new Software();
        software = parser.readFromFile();
        Scanner scanner = new Scanner(System.in);
        String action;

        while (true) {
            System.out.println("m - menu");
            System.out.println("e - exit");
            action = scanner.nextLine();

            switch (action) {
                case "m":
                    menu();
                    break;
                case "e":
                    System.exit(0);
            }

            action = scanner.nextLine();
            switch (action) {
                case "dP":
                    System.out.println("Developers list: ");
                    software.printDevelopers();
                    break;
                case "dA":
                    System.out.println("A new developer's ID: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    System.out.println("A new developer's name: ");
                    String name = scanner.nextLine();

                    System.out.println("A new developer's founder: ");
                    String founder = scanner.nextLine();

                    System.out.println("A new developer's year founded: ");
                    int year = Integer.parseInt(scanner.nextLine());

                    software.addDeveloper(id, name, founder, year);
                    break;
                case "dU":
                    System.out.println("Developer ID: ");
                    id = Integer.parseInt(scanner.nextLine());

                    System.out.println("Rename to: ");
                    name = scanner.nextLine();

                    software.updateDeveloper(id, name);
                    break;
                case "dD":
                    System.out.println("Developer ID: ");
                    id = Integer.parseInt(scanner.nextLine());

                    software.deleteDeveloper(id);
                    break;

                case "pP":
                    System.out.println("Developer's ID: ");
                    id = Integer.parseInt(scanner.nextLine());

                    System.out.println("Software products developed by " + id + ":");
                    software.printProductsOfDeveloper(id);
                    break;
                case "pA":
                    System.out.println("A new software product's ID: ");
                    id = Integer.parseInt(scanner.nextLine());

                    System.out.println("A new software product's name: ");
                    name = scanner.nextLine();

                    System.out.println("A new software product's cost: ");
                    int cost = Integer.parseInt(scanner.nextLine());

                    System.out.println("A new software product's developer ID: ");
                    int developer = Integer.parseInt(scanner.nextLine());

                    software.addProduct(id, name, cost, developer);
                    break;
                case "pU":
                    System.out.println("Software product ID: ");
                    id = Integer.parseInt(scanner.nextLine());

                    System.out.println("Software product's name: ");
                    name = scanner.nextLine();

                    System.out.println("Software product's cost: ");
                    cost = Integer.parseInt(scanner.nextLine());

                    software.updateProduct(id, name, cost);
                    break;
                case "pD":
                    System.out.println("Software product ID: ");
                    id = Integer.parseInt(scanner.nextLine());

                    software.deleteProduct(id);
                    break;
                case "s":
                    Parser.saveToFile(software);
                    break;
                case "e":
                    System.exit(0);
                default :
                    System.out.println("Action was not recognized");
                    break;
            }
        }
    }

    private static void menu() {
        System.out.println("Developers actions: \n"
                + "dP - print all developers \n"
                + "dA - add a new developer \n"
                + "dU - update a developer \n"
                + "dD - delete a developer \n");

        System.out.println("Software product actions: \n"
                + "pP - print all products of the developer \n"
                + "pA - add a new product \n"
                + "pU - update a product \n"
                + "pD - delete a product \n");

        System.out.println("s - save data\n"
                + "e - exit");
    }
}