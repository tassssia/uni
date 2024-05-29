package com.example.lab1Back.dao;

import com.example.lab1Back.model.UsersToursCart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersToursCartDAO extends AbstractDAO {

    public List<UsersToursCart> findAll() {
        List<UsersToursCart> usersToursCartList = new ArrayList<>();
        String sql = "SELECT * FROM users_tours_cart";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                UsersToursCart usersToursCart = new UsersToursCart();
                usersToursCart.setId(resultSet.getInt("id"));
                usersToursCart.setUserId(resultSet.getInt("user_id"));
                usersToursCart.setTourId(resultSet.getInt("tour_id"));
                usersToursCartList.add(usersToursCart);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersToursCartList;
    }

    public UsersToursCart findById(int id) {
        UsersToursCart usersToursCart = null;
        String sql = "SELECT * FROM users_tours_cart WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    usersToursCart = new UsersToursCart();
                    usersToursCart.setId(resultSet.getInt("id"));
                    usersToursCart.setUserId(resultSet.getInt("user_id"));
                    usersToursCart.setTourId(resultSet.getInt("tour_id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersToursCart;
    }

    public void save(UsersToursCart usersToursCart) {
        String sql = "INSERT INTO users_tours_cart (user_id, tour_id) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, usersToursCart.getUserId());
            preparedStatement.setInt(2, usersToursCart.getTourId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(UsersToursCart usersToursCart) {
        String sql = "UPDATE users_tours_cart SET user_id = ?, tour_id = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, usersToursCart.getUserId());
            preparedStatement.setInt(2, usersToursCart.getTourId());
            preparedStatement.setInt(3, usersToursCart.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM users_tours_cart WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByUserIdAndTourId(int userId, int tourId) {
        String sql = "DELETE FROM users_tours_cart WHERE user_id = ? AND tour_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, tourId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}