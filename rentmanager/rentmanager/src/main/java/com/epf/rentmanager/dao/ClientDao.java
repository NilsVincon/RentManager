package com.epf.rentmanager.dao;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.models.Client;

public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?),RETURN_GENERATED_KEYS;";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";

	public long create(Client client) throws DaoException {
        try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase","","");
			PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_CLIENT_QUERY);
			preparedStatement.setString(1, client.getNom());
			preparedStatement.setString( 2, client.getPrenom());
			preparedStatement.setString( 3, client.getEmail());
			preparedStatement.setDate(4, Date.valueOf(client.getNaissance()));
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
                return resultSet.getInt(1);
			}
			connexion.close();
			preparedStatement.close();
			resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } return 0;
	}

	public long delete(Client client) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_CLIENT_QUERY);
			preparedStatement.setInt(1, client.getID_client());
			preparedStatement.execute();
			connexion.close();
			preparedStatement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return client.getID_client();
	}

	public Client findById(long id) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_CLIENT_QUERY);
			preparedStatement.setInt(1, (int) id);
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			String nom = resultSet.getString("nom");
			String prenom = resultSet.getString("prenom");
			String email = resultSet.getString("email");
			LocalDate naissance = resultSet.getDate("naissance").toLocalDate();
			resultSet.close();
			connexion.close();
			preparedStatement.close();
			return new Client((int) id,nom,prenom,email,naissance);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public List<Client> findAll() throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_CLIENTS_QUERY);
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			List<Client> clients = new ArrayList<>();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				LocalDate naissance = resultSet.getDate("naissance").toLocalDate();
				Client client = new Client(id, nom, prenom, email, naissance);
				clients.add(client);
			}
			resultSet.close();
			connexion.close();
			preparedStatement.close();
			return clients;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
