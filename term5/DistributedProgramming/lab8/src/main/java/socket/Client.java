package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

public class Client {
    private final Socket sock;
    private static PrintWriter out;
    private static BufferedReader in;

    public Client(String ip, int port) throws IOException {
        sock = new Socket(ip, port);
        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        out = new PrintWriter(sock.getOutputStream(), true);
    }

    private static String sendRequest(String... requests) {
        StringBuilder request = new StringBuilder();
        for (String arg : requests) {
            request.append(arg).append("#");
        }
        return request.toString();
    }
    private static void readResponse() throws IOException {
        String response = in.readLine();
        String[] fields = response.split("#");
        for (String field : fields) {
            System.out.println(field);
        }
        System.out.println();
    }

    public static void getAllDevelopers() throws IOException {
        out.println(sendRequest("1"));
    }
    public static void getAllProductsOfDeveloper(String id) throws IOException {
        out.println(sendRequest("2", id));
    }
    public static void getAllProductsByName(String name) throws IOException {
        out.println(sendRequest("3", name));
    }
    public static void countAllProductsOfDeveloper(String id) throws IOException {
        out.println(sendRequest("4", id));
    }

    public static void addDeveloper(String id, String name, String founder, String year) throws IOException {
        out.println(sendRequest("5", id, name, founder, year));
    }
    public static void addProduct(String id, String name, String cost, String developer) throws IOException {
        out.println(sendRequest("6", id, name, cost, developer));
    }

    public static void updateDeveloper(String id, String name) throws IOException {
        out.println(sendRequest("7", id, name));
    }
    public static void updateProduct(String id, String name, String cost) throws IOException {
        out.println(sendRequest("8", id, name, cost));
    }

    public static void deleteDeveloper(String id) throws IOException {
        out.println(sendRequest("9", id));
    }
    public static void deleteProduct(String id) throws IOException {
        out.println(sendRequest("10", id));
    }

    public static void stop() throws SQLException {
        out.println(sendRequest("0"));
    }

    public void disconnect() throws IOException {
        sock.close();
    }

    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 12345);

            getAllDevelopers();
            readResponse();

            getAllProductsOfDeveloper("1");
            readResponse();

            countAllProductsOfDeveloper("1");
            readResponse();

            addProduct("6", "name", "66", "1");
            readResponse();
            getAllProductsOfDeveloper("1");
            readResponse();

            updateProduct("6", "another name", "666");
            readResponse();
            getAllProductsOfDeveloper("1");
            readResponse();

            deleteProduct("6");
            readResponse();

            getAllProductsOfDeveloper("1");
            readResponse();

            addDeveloper("6","test","test", "1111");
            readResponse();
            getAllDevelopers();
            readResponse();

            deleteDeveloper("6");
            readResponse();
            getAllDevelopers();
            readResponse();

            stop();
            client.disconnect();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
