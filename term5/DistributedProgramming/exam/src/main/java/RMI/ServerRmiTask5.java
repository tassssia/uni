package RMI;


import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class ServerRmiTask5 {
    public static void main(String[] args) throws RemoteException, InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, AlreadyBoundException {
        final EmailsServerImpl server = new EmailsServerImpl();
        Registry registry = LocateRegistry.createRegistry(123);
        registry.rebind("emails", server);
        Thread.sleep(Integer.MAX_VALUE);
    }
}