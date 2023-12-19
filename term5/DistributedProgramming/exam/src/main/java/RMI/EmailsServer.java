package RMI;

import data.Email;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface EmailsServer extends Remote {
    public ArrayList<Email> getAllEmails() throws RemoteException;
    public ArrayList<Email> getAllEmailsByTopic(String topic) throws RemoteException;
    public void addEmail(int id, String topic) throws RemoteException;
    public void updateEmail(int id, String topic) throws RemoteException;
    public void deleteEmail(int id) throws RemoteException;
}
