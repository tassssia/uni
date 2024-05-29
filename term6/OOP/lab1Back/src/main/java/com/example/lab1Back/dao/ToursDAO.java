package com.example.lab1Back.dao;

import com.example.lab1Back.model.TourType;
import com.example.lab1Back.model.Tours;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToursDAO extends AbstractDAO {

    public List<Tours> findAll() {
        List<Tours> toursList = new ArrayList<>();
        String sql = "SELECT * FROM tours";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Tours tour = new Tours();
                tour.setId(resultSet.getInt("id"));
                tour.setName(resultSet.getString("name"));
                tour.setDescription(resultSet.getString("description"));
                tour.setPrice(resultSet.getDouble("price"));
                tour.setType(TourType.values()[resultSet.getInt("type")]);
                tour.setDate(resultSet.getDate("date"));
                tour.setHot(resultSet.getBoolean("is_hot"));
                tour.setCity(resultSet.getString("city"));
                tour.setTourCompanyId(resultSet.getInt("tour_company_id"));
                toursList.add(tour);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toursList;
    }

    public Tours findById(int id) {
        Tours tour = null;
        String sql = "SELECT * FROM tours WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    tour = new Tours();
                    tour.setId(resultSet.getInt("id"));
                    tour.setName(resultSet.getString("name"));
                    tour.setDescription(resultSet.getString("description"));
                    tour.setPrice(resultSet.getDouble("price"));
                    tour.setType(TourType.values()[resultSet.getInt("type")]);
                    tour.setDate(resultSet.getDate("date"));
                    tour.setHot(resultSet.getBoolean("is_hot"));
                    tour.setCity(resultSet.getString("city"));
                    tour.setTourCompanyId(resultSet.getInt("tour_company_id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tour;
    }

    public void save(Tours tour) {
        String sql = "INSERT INTO tours (name, description, price, type, date, is_hot, city, tour_company_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, tour.getName());
            preparedStatement.setString(2, tour.getDescription());
            preparedStatement.setDouble(3, tour.getPrice());
            preparedStatement.setInt(4, tour.getType().ordinal());
            preparedStatement.setDate(5, new Date(tour.getDate().getTime()));
            preparedStatement.setBoolean(6, tour.isHot());
            preparedStatement.setString(7, tour.getCity());
            preparedStatement.setInt(8, tour.getTourCompanyId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Tours tour) {
        String sql = "UPDATE tours SET name = ?, description = ?, price = ?, type = ?, date = ?, is_hot = ?, city = ?, tour_company_id = ? " +
                "WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, tour.getName());
            preparedStatement.setString(2, tour.getDescription());
            preparedStatement.setDouble(3, tour.getPrice());
            preparedStatement.setInt(4, tour.getType().ordinal());
            preparedStatement.setDate(5, new Date(tour.getDate().getTime()));
            preparedStatement.setBoolean(6, tour.isHot());
            preparedStatement.setString(7, tour.getCity());
            preparedStatement.setInt(8, tour.getTourCompanyId());
            preparedStatement.setInt(9, tour.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM tours WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}