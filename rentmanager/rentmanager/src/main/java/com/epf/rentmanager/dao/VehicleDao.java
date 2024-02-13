package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.exception.DaoException;
public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?),RETURN_GENERATED_KEYS;";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase","","");
			PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_VEHICLE_QUERY);
			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setInt( 2, vehicle.getNb_place());
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

	public long delete(Vehicle vehicle) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_VEHICLE_QUERY);
			preparedStatement.setInt(1, vehicle.getID_vehicle());
			preparedStatement.execute();
			connexion.close();
			preparedStatement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return vehicle.getID_vehicle();
	}

	public Vehicle findById(long id) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_VEHICLE_QUERY);
			preparedStatement.setInt(1, (int) id);
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			String constructeur = resultSet.getString("constructeur");
			int nb_place = resultSet.getInt("nb_places");
			resultSet.close();
			connexion.close();
			preparedStatement.close();
			return new Vehicle((int) id,constructeur,nb_place);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public List<Vehicle> findAll() throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_VEHICLES_QUERY);
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			List<Vehicle> vehicles = new ArrayList<>();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String constructeur = resultSet.getString("constructeur");
				int nb_place = resultSet.getInt("nb_place");
				Vehicle vehicle = new Vehicle(id,constructeur,nb_place);
				vehicles.add(vehicle);
			}
			resultSet.close();
			connexion.close();
			preparedStatement.close();
			return vehicles;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

}
