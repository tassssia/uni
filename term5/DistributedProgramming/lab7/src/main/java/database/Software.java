package database;

import java.sql.*;

public class Software {
    private final Connection con;
    private final Statement stmt;

    public Software(String DBName, String ip, int port) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://" + ip + ":" + port + "/" + DBName;
        con = DriverManager.getConnection(url, "root", "40020182Tp$$");
        stmt = con.createStatement();
    }

    public void stop() throws SQLException {
        con.close();
    }

    public void printDevelopers() {
        String sql = "SELECT * FROM DEVELOPERS";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("DEVELOPERS LIST: ");
            while (rs.next()) {
                int id = rs.getInt("ID_DEV");
                String name = rs.getString("NAME");
                String founder = rs.getString("FOUNDER");
                int year = rs.getInt("YEAR");
                System.out.println(">> " + id + " - " + name + " - " + founder + " - " + year);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("ERROR when getting the list of developers ");
            System.out.println(" >>      " + e.getMessage());
        }
    }
    public void printProductsOfDeveloper(int id) {
        String sql = "SELECT * FROM SOFTWARE_PRODUCTS WHERE ID_DEV = " + id;
        try {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("SOFTWARE PRODUCTS OF THE DEVELOPER " + id + ": ");
            while (rs.next()) {
                int id_pr = rs.getInt("ID_PR");
                String name = rs.getString("NAME");
                int cost = rs.getInt("COST");
                System.out.println(">> " + id_pr + " - " + name + " - " + cost);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("ERROR when getting the list of products");
            System.out.println(" >>      " + e.getMessage());
        }
    }

    public boolean addDeveloper(int id, String name, String founder, int year) {
        String sql = "INSERT INTO DEVELOPERS (ID_DEV, NAME, FOUNDER, YEAR) " +
                "VALUES (" + id + ", '" + name + "', '" + founder + "', " + year + ")";
        try {
            stmt.executeUpdate(sql);
            System.out.println("Developer " + id + " (" + name + ") successfully added");
            return true;
        } catch (SQLException e) {
            System.out.println("ERROR! Developer " + id + " (" + name + ") is NOT added!");
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }
    public boolean addProduct(int id, String name, int cost, int developer) {

        String sql = "INSERT INTO SOFTWARE_PRODUCTS (ID_PR, ID_DEV, NAME, PRICE) " +
                "VALUES (" + id + ", " + developer + ", '" + name + "', " + cost + ")";
        try {
            stmt.executeUpdate(sql);
            System.out.println("Software product " + id + " (" + name + ") successfully added");
            return true;
        } catch (SQLException e) {
            System.out.println("ERROR! Software product " + id + " (" + name + ") is NOT added!");
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }

    public boolean updateDeveloper(int id, String name) {
        String sql = "UPDATE DEVELOPERS SET NAME = '" + name + "' WHERE ID_DEV = " + id;
        try {
            stmt.executeUpdate(sql);
            System.out.println("Developer " + id + " successfully renamed to " + name);
            return true;
        } catch (SQLException e) {
            System.out.println("ERROR! Developer " + id + " is NOT renamed to " + name);
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }
    public boolean updateProduct(int id, String name, int cost) {
        String sql = "UPDATE SOFTWARE_PRODUCTS SET NAME = '" + name + "', COST = " + cost + " WHERE ID_PR = " + id;

        try {
            stmt.executeUpdate(sql);
            System.out.println("Software product " + id + " successfully updated");
            return true;
        } catch (SQLException e) {
            System.out.println("ERROR! Software product " + id + " is NOT updated");
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }

    public boolean deleteDeveloper(int id) {
        String sql = "DELETE FROM DEVELOPERS WHERE ID_DEV = " + id;
        try {
            int c = stmt.executeUpdate(sql);
            if (c > 0) {
                System.out.println("Developer " + id + " successfully deleted!");
                return true;
            } else {
                System.out.println("Developer " + id + " not found!");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("ERROR when deleting developer " + id);
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }
    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM SOFTWARE_PRODUCTS WHERE ID_PR = " + id;
        try {
            int c = stmt.executeUpdate(sql);
            if (c > 0) {
                System.out.println("Software product " + id + " successfully deleted!");
                return true;
            } else {
                System.out.println("Software product " + id + " not found!");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("ERROR when deleting software product " + id);
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        Software s = new Software("software", "localhost", 3306);

        s.printDevelopers();

        s.addDeveloper(3, "TEST_DEV1", "FOUNDER1", 2001);
        s.addDeveloper(4, "TEST_DEV2", "FOUNDER2", 2002);

        s.deleteDeveloper(3);
        s.deleteDeveloper(7);
        s.printDevelopers();

        s.deleteDeveloper(4);

        System.out.println("---------------------------------");

        s.printProductsOfDeveloper(11);

        s.printProductsOfDeveloper(1);
        s.updateProduct(1, "NewName", 50);
        s.printProductsOfDeveloper(1);

        s.updateProduct(1, "WordProcessor", 50);

        s.stop();
    }
}
