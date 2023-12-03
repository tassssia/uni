package task1;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    public void send(String sender, String text) {

        try {
            Socket socket = new Socket("localhost", 1111);
            sendToStream(new Message(sender, text), socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

     private void sendToStream(Message message, Socket socket) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}