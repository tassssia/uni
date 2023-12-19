package RMI;

import data.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SoftwareServer extends Remote {
    public ArrayList<Developer> getAllDevelopers() throws RemoteException;
    public ArrayList<SoftwareProduct> getAllProductsOfDeveloper(int id) throws RemoteException;
    public ArrayList<SoftwareProduct> getAllProductsByName(String name) throws RemoteException;
    public int countAllProductsOfDeveloper(int id) throws RemoteException;

    public void addDeveloper(int id, String name, String founder, int year) throws RemoteException;
    public void addProduct(int id, String name, int cost, int developer) throws RemoteException;

    public void updateDeveloper(int id, String name) throws RemoteException;
    public void updateProduct(int id, String name, int cost) throws RemoteException;

    public void deleteDeveloper(int id) throws RemoteException;
    public void deleteProduct(int id) throws RemoteException;
}
