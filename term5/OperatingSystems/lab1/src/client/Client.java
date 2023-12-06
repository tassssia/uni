package client;

import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.util.Optional;

public class Client {
    private final File socketFile = new File(new File(System.getProperty("java.io.tmpdir")), "junixsocket-test.sock");
    private final String funcType;
    private Integer argument;

    public Client(String funcType) {
        this.funcType = funcType;
        compute();
    }

    public void read() {
        try (AFUNIXSocket socket = AFUNIXSocket.newInstance()) {
            socket.connect(AFUNIXSocketAddress.of(socketFile));
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            argument = (Integer)input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void send(String result){
        try (AFUNIXSocket socket = AFUNIXSocket.newInstance()) {
            socket.connect(AFUNIXSocketAddress.of(socketFile));
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

            output.writeObject(result);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compute() {
        read();

        Optional<Integer> res = Optional.empty();
        SecureRandom random = new SecureRandom();
        int randomF = random.nextInt(15);
        int randomG = random.nextInt(15);
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (randomF < 10) {
            if (funcType.equals("F")) {
                res = AlgFunctions.algF(argument);
            }
        }

        if (randomG < 10) {
            if (funcType.equals("G")) {
                res = AlgFunctions.algG(argument);
            }
        }
        else if (randomG < 12)
            while(true) {
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        String result = res.map(integer -> funcType + integer).orElseGet(() -> funcType + "hard fail");
        send(result);
    }
}