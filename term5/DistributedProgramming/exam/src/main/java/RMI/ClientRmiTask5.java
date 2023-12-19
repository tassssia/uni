package RMI;

import data.Email;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientRmiTask5 {
    private static EmailsServer server;

    public static String getAllEmails() throws IOException {
        StringBuilder result = new StringBuilder();
        result.append("ALL EMAILS:\n");

        ArrayList<Email> emails = server.getAllEmails();
        for (Email em : emails) {
            result.append(em.toString() + "\n");
        }
        return result.toString();
    }
    public static String getAllEmailsByTopic(String topic) throws IOException {
        StringBuilder result = new StringBuilder();
        result.append("EMAILS WITH TOPIC '"+ topic +"': \n");

        ArrayList<Email> emails = server.getAllEmailsByTopic(topic);
        for (Email em : emails) {
            result.append(em.toString() + "\n");
        }
        return result.toString();
    }

    public static void addEmail(String id, String topic) throws IOException {
        server.addEmail(Integer.parseInt(id), topic);
    }
    public static void updateEmail(String id, String topic) throws IOException {
        server.updateEmail(Integer.parseInt(id), topic);
    }
    public static void deleteEmail(String id) throws IOException {
        server.deleteEmail(Integer.parseInt(id));
    }

    public static void main(String[] args) throws NotBoundException, IOException {
        String url = "//localhost:123/emails";
        server = (EmailsServer)Naming.lookup(url);

        System.out.println("Connected to the server.");

        System.out.println(getAllEmails());
        System.out.println(getAllEmailsByTopic("TOPIC_1"));

        addEmail("11", "test");
        System.out.println(getAllEmails());

        updateEmail("11", "topic");
        System.out.println(getAllEmails());

        deleteEmail("11");
        System.out.println(getAllEmails());
    }
}
