package com.epf.rentmanager.dao;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository // Annotation Spring indiquant que cette classe est un bean géré par Spring
public class ClientDao {

    // Suppression de la méthode getInstance() car Spring gérera l'instanciation de ce bean

    private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?)";
    private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?";
    private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?";
    private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client";
    private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(*) AS total FROM Client";
    private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?";

    public long create(Client client) throws DaoException {
        try (Connection connexion = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, client.getNom());
            preparedStatement.setString(2, client.getPrenom());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setDate(4, Date.valueOf(client.getNaissance()));
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return 0;
    }

    public long delete(Client client) throws DaoException {
        try (Connection connexion = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_CLIENT_QUERY)) {
            preparedStatement.setInt(1, client.getID_client());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return client.getID_client();
    }

    public Client findById(int id) throws DaoException {
        try (Connection connexion = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connexion.prepareStatement(FIND_CLIENT_QUERY)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                LocalDate naissance = resultSet.getDate("naissance").toLocalDate();
                return new Client((int) id, nom, prenom, email, naissance);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return null;
    }

    public List<Client> findAll() throws DaoException {
        List<Client> clients = new ArrayList<>();
        try (Connection connexion = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connexion.prepareStatement(FIND_CLIENTS_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                LocalDate naissance = resultSet.getDate("naissance").toLocalDate();
                clients.add(new Client(id, nom, prenom, email, naissance));
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return clients;
    }

    public int count() throws DaoException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_CLIENTS_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return 0;
    }

    public void update(Client newClient) throws SQLException {
        Connection connexion = null;
        try {
            connexion = ConnectionManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        PreparedStatement preparedStatement = connexion.prepareStatement(UPDATE_CLIENT_QUERY);
        preparedStatement.setString(1, newClient.getNom());
        preparedStatement.setString(2, newClient.getPrenom());
        preparedStatement.setString(3, newClient.getEmail());
        preparedStatement.setDate(4, Date.valueOf(newClient.getNaissance()));
        preparedStatement.setInt(5, newClient.getID_client());
        preparedStatement.executeUpdate();
    }
}
