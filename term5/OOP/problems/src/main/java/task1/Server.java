package task1;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Server {
    private String defaultOutputFile;

    public String getDefaultOutputFile() {
        return defaultOutputFile;
    }
    public void setDefaultOutputFile(String defaultOutputFile) {
        this.defaultOutputFile = defaultOutputFile;
    }

    public synchronized void save(Message myObject, String outputFile) {
        String str = messageObjToStr(myObject);

        try {
            File file = new File(outputFile);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(outputFile, false)) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receive(Socket clientSocket) throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

        try {
            Object obj = objectInputStream.readObject();
            Message message = (Message) obj;

            save(message, defaultOutputFile);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        clientSocket.close();
    }

    private String messageObjToStr(Message message) {
        return "Message '" + message.getText() + "' by " + message.getSender();
    }
}