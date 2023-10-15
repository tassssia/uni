public class taskC {
    public static void main(String[] args) {
        Graph routes = new Graph(5);
        GraphThread thread1 = new PriceChanger(routes);
        GraphThread thread2 = new WaysEditor(routes);
        GraphThread thread3 = new CitiesEditor(routes);
        GraphThread thread4 = new WaySearcher(routes);

        thread1.setDaemon(true);
        thread2.setDaemon(true);
        thread3.setDaemon(true);
        thread4.setDaemon(true);

        try {
            thread4.start();
            thread1.start();
            Thread.sleep(500);
            thread2.start();
            Thread.sleep(500);
            thread3.start();

            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } 
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
