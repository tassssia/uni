import java.util.Random;
import java.security.SecureRandom;
public abstract class GraphThread extends Thread {
    Graph graph;
}

class PriceChanger extends GraphThread {
    public PriceChanger(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void run() {
        SecureRandom random = new SecureRandom();
        while (true) {
            int i = random.nextInt(graph.getSize());
            int j = random.nextInt(graph.getSize());
            int newPrice = random.nextInt(100) + 1;

            try {
                while (graph.getCost(i, j) == 0) {
                    i = random.nextInt(graph.getSize());
                    j = random.nextInt(graph.getSize());
                    newPrice = random.nextInt(100) + 1;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            graph.LockWrite(i, j);
            System.out.println("Changing cost of " + i + " - " + j);
            try {
                graph.setCost(i, j, newPrice);
                System.out.println("Set cost of " + i + " - " + j + " to " + newPrice);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Finished changing costs");
            graph.UnlockWrite(i, j);

            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class WaysEditor extends GraphThread {
    public WaysEditor(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void run() {
        SecureRandom random = new SecureRandom();
        while (true) {
            int i = random.nextInt(graph.getSize());
            int j = random.nextInt(graph.getSize());

            graph.LockWrite(i, j);
            System.out.println("Editing way " + i + " - " + j);
            try {
                if (graph.getCost(i, j) == 0) {
                    graph.setCost(i, j, random.nextInt(100) + 1);
                    System.out.println("Way " + i + " - " + j + " was added with a cost of " + graph.getCost(i, j));
                }
                else {
                    graph.setCost(i, j, 0);
                    System.out.println("Way " + i + " - " + j + " was removed");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Finished editing ways");
            graph.UnlockWrite(i, j);

            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class CitiesEditor extends GraphThread {
    public CitiesEditor(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void run() {
        SecureRandom random = new SecureRandom();
        int city = random.nextInt(graph.getSize() * 2);

        graph.LockWrite();
        System.out.println("Editing cities");
        try {
            if (city < graph.getSize()) {
                graph.removeCity(city);
                System.out.println("The city " + city + " was removed");
            } else {
                graph.addCity();
                System.out.println("One more city was added, total number of them is " + graph.getSize() + "now");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Finished editing cities");
        graph.UnlockWrite();

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class WaySearcher extends GraphThread {
    public WaySearcher(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void run() {
        SecureRandom random = new SecureRandom();
        while (true) {
            int city1 = random.nextInt(graph.getSize());
            int city2 = city1;
            while (city2 == city1) {
                city2 = random.nextInt(graph.getSize());
            }

            graph.LockRead();
            System.out.println("Started searching way between " + city1 + " - " + city2);
            try {
                int way = graph.calcCost(city1, city2);
                if (way == 0) {
                    System.out.println("There is no way between " + city1 + " - " + city2);
                } else {
                    System.out.println("The way between " + city1 + " - " + city2 + " costs " + way);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Finished searching");
            graph.UnlockRead();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}