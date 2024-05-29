package com.example.lab1Back.dao;

import com.example.lab1Back.model.UsersDiscount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDiscountDAO extends AbstractDAO {

    public List<UsersDiscount> findAll() {
        List<UsersDiscount> usersDiscountList = new ArrayList<>();
        String sql = "SELECT * FROM users_discount";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                UsersDiscount usersDiscount = new UsersDiscount();
                usersDiscount.setId(resultSet.getInt("id"));
                usersDiscount.setUserId(resultSet.getInt("user_id"));
                usersDiscount.setTourCompanyId(resultSet.getInt("tour_company_id"));
                usersDiscount.setDiscount(resultSet.getInt("discount"));
                usersDiscountList.add(usersDiscount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersDiscountList;
    }

    public UsersDiscount findById(int id) {
        UsersDiscount usersDiscount = null;
        String sql = "SELECT * FROM users_discount WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    usersDiscount = new UsersDiscount();
                    usersDiscount.setId(resultSet.getInt("id"));
                    usersDiscount.setUserId(resultSet.getInt("user_id"));
                    usersDiscount.setTourCompanyId(resultSet.getInt("tour_company_id"));
                    usersDiscount.setDiscount(resultSet.getInt("discount"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersDiscount;
    }

    public void save(UsersDiscount usersDiscount) {
        String sql = "INSERT INTO users_discount (user_id, tour_company_id, discount) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, usersDiscount.getUserId());
            preparedStatement.setInt(2, usersDiscount.getTourCompanyId());
            preparedStatement.setInt(3, usersDiscount.getDiscount());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(UsersDiscount usersDiscount) {
        String sql = "UPDATE users_discount SET user_id = ?, tour_company_id = ?, discount = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, usersDiscount.getUserId());
            preparedStatement.setInt(2, usersDiscount.getTourCompanyId());
            preparedStatement.setInt(3, usersDiscount.getDiscount());
            preparedStatement.setInt(4, usersDiscount.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM users_discount WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}