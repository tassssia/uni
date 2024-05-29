package com.example.lab1Back.dao;

import com.example.lab1Back.model.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO extends AbstractDAO {

    public List<Users> findAll() {
        List<Users> usersList = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Users user = new Users();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setMoney(resultSet.getDouble("money"));
                user.setPassword(resultSet.getString("password"));
                usersList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersList;
    }

    public Users findById(int id) {
        Users user = null;
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new Users();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setMoney(resultSet.getDouble("money"));
                    user.setPassword(resultSet.getString("password"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public Users findByName(String name) {
        Users user = null;
        String sql = "SELECT * FROM users WHERE name = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new Users();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setMoney(resultSet.getDouble("money"));
                    user.setPassword(resultSet.getString("password"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void save(Users user) {
        String sql = "INSERT INTO users (name, money, password) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setDouble(2, user.getMoney());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Users user) {
        String sql = "UPDATE users SET name = ?, money = ?, password = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setDouble(2, user.getMoney());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}