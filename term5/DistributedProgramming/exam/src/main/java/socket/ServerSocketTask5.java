package socket;

import data.Email;
import database.EmailDB;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerSocketTask5 {
    private ServerSocket server = null;
    private Socket sock = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private EmailDB service = null;

    public void start(int port) throws Exception {
        server = new ServerSocket(port);
        service = new EmailDB();
        while (true) {
            sock = server.accept();
            in = new ObjectInputStream(sock.getInputStream());
            out = new ObjectOutputStream(sock.getOutputStream());
            while (processQuery()) ;
        }
    }

    private boolean processQuery() throws SQLException {
        try {
            String command = (String) in.readObject();
            switch (command) {
                case "1":
                    ArrayList<Email> emails = service.getAllEmails();
                    out.writeObject(emails);
                    break;
                case "2":
                    String topic = (String)in.readObject();
                    emails = service.getAllEmailsByTopic(topic);
                    out.writeObject(emails);
                    break;
                case "3":
                    int id = (int)in.readObject();
                    topic = (String)in.readObject();
                    service.addEmail(id, topic);
                    break;
                case "4":
                    id = (int)in.readObject();
                    topic = (String)in.readObject();
                    service.updateEmail(id, topic);
                    break;
                case "5":
                    id = (int)in.readObject();
                    service.deleteEmail(id);
                    break;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocketTask5 srv = new ServerSocketTask5();
            srv.start(12345);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}