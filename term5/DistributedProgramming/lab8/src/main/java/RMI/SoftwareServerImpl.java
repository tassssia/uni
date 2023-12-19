package RMI;

import data.Developer;
import data.SoftwareProduct;
import database.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class SoftwareServerImpl extends UnicastRemoteObject implements SoftwareServer {
    private static SoftwareDB service;

    public SoftwareServerImpl() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, RemoteException {
        service = new SoftwareDB("software", "localhost", 3306);
    }


    @Override
    public ArrayList<Developer> getAllDevelopers() throws RemoteException {
        return service.getAllDevelopers();
    }
    @Override
    public ArrayList<SoftwareProduct> getAllProductsOfDeveloper(int id) throws RemoteException {
        return service.getAllProductsOfDeveloper(id);
    }
    @Override
    public ArrayList<SoftwareProduct> getAllProductsByName(String name) throws RemoteException {
        return service.getAllProductsByName(name);
    }
    @Override
    public int countAllProductsOfDeveloper(int id) throws RemoteException {
        return service.countAllProductsOfDeveloper(id);
    }

    @Override
    public void addDeveloper(int id, String name, String founder, int year) throws RemoteException {
        service.addDeveloper(id, name, founder, year);
    }
    @Override
    public void addProduct(int id, String name, int cost, int developer) throws RemoteException {
        service.addProduct(id, name, cost, developer);
    }

    @Override
    public void updateDeveloper(int id, String name) throws RemoteException {
        service.updateDeveloper(id, name);
    }
    @Override
    public void updateProduct(int id, String name, int cost) throws RemoteException {
        service.updateProduct(id, name, cost);
    }

    @Override
    public void deleteDeveloper(int id) throws RemoteException {
        service.deleteDeveloper(id);
    }
    @Override
    public void deleteProduct(int id) throws RemoteException {
        service.deleteProduct(id);
    }
}
