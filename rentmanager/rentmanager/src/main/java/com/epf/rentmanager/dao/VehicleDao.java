package com.epf.rentmanager.dao;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.models.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository // Annotation Spring indiquant que cette classe est un bean géré par Spring
public class VehicleDao {

	// Suppression de la méthode getInstance() car Spring gérera l'instanciation de ce bean

	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur,model, nb_places) VALUES(?, ?,?)";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur,model, nb_places FROM Vehicle WHERE id=?";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur,model, nb_places FROM Vehicle";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(*) AS total FROM Vehicle";

	public long create(Vehicle vehicle) throws DaoException {
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setString(2, vehicle.getModel());
			preparedStatement.setInt(3, vehicle.getNb_place());
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("cho pour toi");
			throw new DaoException(e.getMessage(), e);
		}
		return 0;
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_VEHICLE_QUERY)) {
			preparedStatement.setInt(1, vehicle.getID_vehicle());
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return vehicle.getID_vehicle();
	}

	public Vehicle findById(long id) throws DaoException {
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connexion.prepareStatement(FIND_VEHICLE_QUERY)) {
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String constructeur = resultSet.getString("constructeur");
				String model = resultSet.getString("model");
				int nb_place = resultSet.getInt("nb_places");
				return new Vehicle((int) id, constructeur,model, nb_place);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return null;
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> vehicles = new ArrayList<>();
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connexion.prepareStatement(FIND_VEHICLES_QUERY)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String constructeur = resultSet.getString("constructeur");
				String model = resultSet.getString("model");
				int nb_place = resultSet.getInt("nb_places");
				vehicles.add(new Vehicle(id, constructeur,model, nb_place));
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return vehicles;
	}

	public int count() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(COUNT_VEHICLES_QUERY)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("total");
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return 0;
	}
}
