package com.example.lab1Back.dao;

import com.example.lab1Back.model.TourCompany;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TourCompanyDao extends AbstractDAO {

    public List<TourCompany> findAll() {
        List<TourCompany> tourCompanyList = new ArrayList<>();
        String sql = "SELECT * FROM tour_company";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                TourCompany tourCompany = new TourCompany();
                tourCompany.setId(resultSet.getInt("id"));
                tourCompany.setName(resultSet.getString("name"));
                tourCompanyList.add(tourCompany);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tourCompanyList;
    }

    public TourCompany findById(int id) {
        TourCompany tourCompany = null;
        String sql = "SELECT * FROM tour_company WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    tourCompany = new TourCompany();
                    tourCompany.setId(resultSet.getInt("id"));
                    tourCompany.setName(resultSet.getString("name"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tourCompany;
    }

    public void save(TourCompany tourCompany) {
        String sql = "INSERT INTO tour_company (name) VALUES (?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, tourCompany.getName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(TourCompany tourCompany) {
        String sql = "UPDATE tour_company SET name = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, tourCompany.getName());
            preparedStatement.setInt(2, tourCompany.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM tour_company WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}