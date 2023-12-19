package database;

import data.*;
import java.sql.*;
import java.util.ArrayList;

public class EmailDB {
    private final Connection con;
    private final Statement stmt;
    private final StringBuilder res;

    public EmailDB() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        String DBName = "emails"; String ip = "localhost"; int port = 3306;
        String url = "jdbc:mysql://" + ip + ":" + port + "/" + DBName;
        con = DriverManager.getConnection(url, "root", "40020182Tp$$");
        stmt = con.createStatement();
        res = new StringBuilder();
    }

    public void stop() throws SQLException {
        con.close();
    }

    public ArrayList<Email> getAllEmails() {
        String sql = "SELECT * FROM EMAILS";
        try {
            ResultSet rs = stmt.executeQuery(sql);

            ArrayList<Email> emails = new ArrayList<>();
            while (rs.next()) {
                emails.add(new Email(rs.getInt("ID"), rs.getString("TOPIC")));
            }
            rs.close();
            return emails;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Email> getAllEmailsByTopic(String topic) {
        String sql = "SELECT * FROM EMAILS WHERE TOPIC = '" + topic + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);

            ArrayList<Email> emails = new ArrayList<>();
            while (rs.next()) {
                emails.add(new Email(rs.getInt("ID"), rs.getString("TOPIC")));
            }
            rs.close();
            return emails;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addEmail(int id, String topic) {
        String sql = "INSERT INTO EMAILS (ID, TOPIC) " + "VALUES (" + id + ", '" + topic + "')";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEmail(int id, String topic) {
        String sql = "UPDATE EMAILS SET TOPIC = '" + topic + "' WHERE ID = " + id;
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEmail(int id) {
        String sql = "DELETE FROM EMAILS WHERE ID = " + id;
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
