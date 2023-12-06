package manager;

import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import sun.misc.Signal;

public class Manager {
    private final Integer argument;
    private final AFUNIXServerSocket server;
    private final ProcessBuilder processBuilderF;
    private final ProcessBuilder processBuilderG;
    private Process F;
    private Process G;
    private Boolean failF;
    private Boolean failG;
    public Boolean isCanceled;
    private final ArrayList<Integer> results;

    Manager(Integer argument) throws IOException {
        System.out.println("Manager has started");

        this.argument = argument;

        initSignalHandler();

        File socketFile = new File(new File(System.getProperty("java.io.tmpdir")), "junixsocket-test.sock");

        server = AFUNIXServerSocket.newInstance();
        server.bind(AFUNIXSocketAddress.of(socketFile));

        processBuilderF = new ProcessBuilder("java", "-jar", "./out/artifacts/Lab1F/Lab1.jar");
        failF = false;
        processBuilderG = new ProcessBuilder("java", "-jar", "./out/artifacts/Lab1G/Lab1.jar");
        failG = false;

        isCanceled = false;
        results = new ArrayList<>();

        run();
    }

    public void run() {
        CompletableFuture<Void> futureF = CompletableFuture.runAsync(() -> {
            try {
                F = processBuilderF.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        CompletableFuture<Void> futureG = CompletableFuture.runAsync(() -> {
            try {
                G = processBuilderG.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futureF, futureG);

        combinedFuture.thenRunAsync(this::send).thenRunAsync(this::checkAlive);
    }
    private void send() {
        for(int i = 0; i < 2; i++) {
            try (Socket socket = server.accept()) {
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(argument);
                output.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void checkAlive() {
        AtomicBoolean isFAlive = new AtomicBoolean(true);
        AtomicBoolean isGAlive = new AtomicBoolean(true);
        int counter = 0;

        long tStart = System.currentTimeMillis();

        while (true) {
            if(!F.isAlive() && isFAlive.get()){
                read();

                isFAlive.set(false);
                counter++;
                if(counter == 2) {
                    break;
                }
            } else if(isCanceled) {
                break;
            }

            if(!G.isAlive() && isGAlive.get()){
                read();

                isGAlive.set(false);
                counter++;
                if(counter == 2) {
                    break;
                }
            } else if(isCanceled) {
                break;
            }

            if(System.currentTimeMillis() - tStart > 20000) {
                System.out.println("G is out of time. Cannot compute");
                break;
            }
        }
    }
    private void read(){
        try (Socket socket = server.accept()) {
            if(isCanceled) {
                return;
            }

            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            String res = (String)input.readObject();
            String funcType = res.substring(0, 1);
            res = res.substring(1);

            if(res.equals("hard fail")){
                failF = funcType.equals("F");
                failG = funcType.equals("G");

                System.out.println("Function " + funcType + " hard failed");
            } else {
                results.add(Integer.valueOf(res));

                if(funcType.equals("F")) {
                    System.out.println("Function F: " + res);
                }
                else {
                    System.out.println("Function G: " + res);
                }
            }
        } catch (IOException | ClassNotFoundException e ) {
            e.printStackTrace();
        }
    }

    private void cancelManually() {
        System.out.println("Stop computation? [y/n]");

        long tStart = System.currentTimeMillis();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            if(System.currentTimeMillis() - tStart > 5000) {
                System.out.println("No responce for 5 seconds. Proceeding...");
                initSignalHandler();
                return;
            } else if(results.size() == 2) {
                System.out.println("Overriden by system");
                initSignalHandler();
                return;
            } else {
                try {
                    if (reader.ready()){
                        String answer = reader.readLine();

                        if(!answer.isEmpty()){
                            if(answer.equals("y")){
                                System.out.println("Cancelling...");
                                isCanceled = true;
                                return;
                            } else if (answer.equals("n")) {
                                initSignalHandler();
                                System.out.println("Proceeding...");
                                return;
                            } else {
                                System.out.println("Responce is not recognized");
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void destroyProcs() {
        F.destroy();
        G.destroy();
    }

    private void initSignalHandler() {
        Signal.handle(new Signal("INT"), signal -> cancelManually());
    }

    public int getStatusF(){
        return failF ? 1 : F.isAlive() ? -1 : 0;
    }
    public int getStatusG(){
        return failG ? 1 : G.isAlive() ? -1 : 0;
    }
    public int getResult(){
        return results.get(0) + results.get(1);
    }
}