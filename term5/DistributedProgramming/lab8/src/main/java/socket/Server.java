package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket server = null;
    private static Socket sock = null;
    private static PrintWriter out = null;
    private static BufferedReader in = null;

    public static void main(String[] args) {
        try {
            Server srv = new Server();
            srv.start(12345);
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public void start(int port) throws IOException {
        server = new ServerSocket(port);

        while (true) {
            sock = server.accept();
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(sock.getOutputStream(), true);

            while (processQuery()) ;
        }
    }

    public static boolean processQuery() {
        Services service = new Services();
        try {
            String query = in.readLine();
            String[] parameters = query.split("#");
            String command = parameters[0];
            String response = "";

            switch (command) {
                case "1":
                    response = service.getAllDevelopers();
                    break;
                case "2":
                    response = service.getAllProductsOfDeveloper(Integer.parseInt(parameters[1]));
                    break;
                case "3":
                    response = service.getAllProductsByName(parameters[1]);
                    break;
                case "4":
                    response = service.countAllProductsOfDeveloper(Integer.parseInt(parameters[1]));
                    break;
                case "5":
                    response = service.addDeveloper(Integer.parseInt(parameters[1]), parameters[2], parameters[3], Integer.parseInt(parameters[4]));
                    break;
                case "6":
                    response = service.addProduct(Integer.parseInt(parameters[1]), parameters[2], Integer.parseInt(parameters[3]), Integer.parseInt(parameters[4]));
                    break;
                case "7":
                    response = service.updateDeveloper(Integer.parseInt(parameters[1]), parameters[2]);
                    break;
                case "8":
                    response = service.updateProduct(Integer.parseInt(parameters[1]), parameters[2], Integer.parseInt(parameters[3]));
                    break;
                case "9":
                    response = service.deleteDeveloper(Integer.parseInt(parameters[1]));
                    break;
                case "10":
                    response = service.deleteProduct(Integer.parseInt(parameters[1]));
                    break;
                case "0":
                    sock.close();
                    in.close();
                    out.close();
                    return false;
            }

            out.println(response);
            out.flush();

            return true;
        } catch (Exception e) {
            System.out.println("SERVER: Error: " + e.getMessage());
            return false;
        }
    }
}