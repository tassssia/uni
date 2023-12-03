package task1;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientServerTest {
    private static final int PORT = 1111;
    private static final String TEST_FILE = "test_output.txt";
    private Server server;

    @BeforeEach
    void setUp() {
        server = new Server();
        server.setDefaultOutputFile(TEST_FILE);
    }

    @AfterEach
    void tearDown() {
        java.io.File file = new java.io.File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testSendAndReceive() {
        Thread serverThread = new Thread(() -> {
            try {
                server.setDefaultOutputFile(TEST_FILE);
                ServerSocket serverSocket = new ServerSocket(PORT);

                Socket clientSocket = serverSocket.accept();
                server.receive(clientSocket);

                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        serverThread.start();

        Client client = new Client();
        client.send("Sender1", "Hello, Server!");

        try {
            serverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("Message 'Hello, Server!' by Sender1", readTestFileContent());
    }

    private String readTestFileContent() {
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(TEST_FILE))) {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
