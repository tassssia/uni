package RMI;

import data.Email;
import database.EmailDB;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmailsServerImpl extends UnicastRemoteObject implements EmailsServer {
    private static EmailDB service;
    public EmailsServerImpl() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, RemoteException {
        service = new EmailDB();
    }

    @Override
    public ArrayList<Email> getAllEmails() throws RemoteException {
        return service.getAllEmails();
    }
    @Override
    public ArrayList<Email> getAllEmailsByTopic(String topic) throws RemoteException {
        return service.getAllEmailsByTopic(topic);
    }
    @Override
    public void addEmail(int id, String topic) throws RemoteException {
        service.addEmail(id, topic);
    }
    @Override
    public void updateEmail(int id, String topic) throws RemoteException {
        service.updateEmail(id, topic);
    }
    @Override
    public void deleteEmail(int id) throws RemoteException {
        service.deleteEmail(id);
    }
}
