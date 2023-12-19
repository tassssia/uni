package database;

import data.*;
import java.sql.*;
import java.util.ArrayList;

public class SoftwareDB {
    private final Connection con;
    private final Statement stmt;
    private final StringBuilder res;

    public SoftwareDB(String DBName, String ip, int port) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://" + ip + ":" + port + "/" + DBName;
        con = DriverManager.getConnection(url, "root", "40020182Tp$$");
        stmt = con.createStatement();
        res = new StringBuilder();
    }

    public void stop() throws SQLException {
        con.close();
    }

    public ArrayList<Developer> getAllDevelopers() {
        String sql = "SELECT * FROM DEVELOPERS";
        try {
            ResultSet rs = stmt.executeQuery(sql);

            ArrayList<Developer> developers = new ArrayList<>();
            while (rs.next()) {
                developers.add(new Developer(rs.getInt("ID_DEV"),
                        rs.getString("NAME"),
                        rs.getString("FOUNDER"),
                        rs.getInt("YEAR")));
            }
            rs.close();
            return developers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<SoftwareProduct> getAllProductsOfDeveloper(int id) {
        String sql = "SELECT * FROM SOFTWARE_PRODUCTS WHERE ID_DEV = " + id;
        try {
            ResultSet rs = stmt.executeQuery(sql);

            ArrayList<SoftwareProduct> products = new ArrayList<>();
            while (rs.next()) {
                products.add(new SoftwareProduct(rs.getInt("ID_PR"),
                        rs.getString("NAME"),
                        rs.getInt("COST"),
                        rs.getInt("ID_DEV")));
            }
            rs.close();
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<SoftwareProduct> getAllProductsByName(String name) {
        String sql = "SELECT * FROM SOFTWARE_PRODUCTS WHERE NAME = '" + name + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);

            ArrayList<SoftwareProduct> products = new ArrayList<>();
            while (rs.next()) {
                products.add(new SoftwareProduct(rs.getInt("ID_PR"),
                        rs.getString("NAME"),
                        rs.getInt("COST"),
                        rs.getInt("ID_DEV")));
            }
            rs.close();
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int countAllProductsOfDeveloper(int id) {
        String sql = "SELECT COUNT(*) FROM SOFTWARE_PRODUCTS WHERE ID_DEV = " + id;
        try {
            ResultSet rs = stmt.executeQuery(sql);

            int count = rs.next() ? rs.getInt(1) : 0;
            rs.close();
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addDeveloper(int id, String name, String founder, int year) {
        String sql = "INSERT INTO DEVELOPERS (ID_DEV, NAME, FOUNDER, YEAR) " +
                "VALUES (" + id + ", '" + name + "', '" + founder + "', " + year + ")";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addProduct(int id, String name, int cost, int developer) {

        String sql = "INSERT INTO SOFTWARE_PRODUCTS (ID_PR, ID_DEV, NAME, COST) " +
                "VALUES (" + id + ", " + developer + ", '" + name + "', " + cost + ")";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDeveloper(int id, String name) {
        String sql = "UPDATE DEVELOPERS SET NAME = '" + name + "' WHERE ID_DEV = " + id;
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateProduct(int id, String name, int cost) {
        String sql = "UPDATE SOFTWARE_PRODUCTS SET NAME = '" + name + "', COST = " + cost + " WHERE ID_PR = " + id;
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDeveloper(int id) {
        String sql = "DELETE FROM DEVELOPERS WHERE ID_DEV = " + id;
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteProduct(int id) {
        String sql = "DELETE FROM SOFTWARE_PRODUCTS WHERE ID_PR = " + id;
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
