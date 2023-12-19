package socket;

import data.Email;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class ClientSocketTask5 {
    private Socket sock = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    public ClientSocketTask5(String ip, int port) throws IOException {
        sock = new Socket(ip,port);
        out = new ObjectOutputStream(sock.getOutputStream());
        in = new ObjectInputStream(sock.getInputStream());
    }

    public ArrayList<Email> getAllEmails() throws IOException, ClassNotFoundException {
        out.writeObject("1");
        return (ArrayList<Email>)in.readObject();
    }
    public ArrayList<Email> getAllEmailsByTopic(String topic) throws IOException, ClassNotFoundException {
        out.writeObject("2");
        out.writeObject(topic);
        return (ArrayList<Email>)in.readObject();
    }
    public void addEmail(int id, String topic) throws IOException {
        out.writeObject("3");
        out.writeObject(id);
        out.writeObject(topic);
    }
    public void updateEmail(int id, String topic) throws IOException {
        out.writeObject("4");
        out.writeObject(id);
        out.writeObject(topic);
    }
    public void deleteEmail(int id) throws IOException {
        out.writeObject("5");
        out.writeObject(id);
    }

    public void disconnect() throws IOException {
        sock.close();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClientSocketTask5 client = new ClientSocketTask5("localhost",12345);

        client.deleteEmail(11);
        System.out.println("ALL EMAILS: ");
        ArrayList<Email> emails = client.getAllEmails();
        for (Email em: emails){
            System.out.println(em.toString());
        }

    }
}
