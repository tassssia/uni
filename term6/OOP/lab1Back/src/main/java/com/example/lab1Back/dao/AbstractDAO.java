package com.example.lab1Back.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AbstractDAO {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/your_database_name";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    private static final Connection connection;

    static {
        try {
            connection = getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }
}
