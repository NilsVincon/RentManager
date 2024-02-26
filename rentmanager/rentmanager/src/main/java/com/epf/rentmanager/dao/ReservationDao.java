package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.models.Reservation;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?),RETURN_GENERATED_KEYS;";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_ID_QUERY = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase","","");
			PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_RESERVATION_QUERY);
			preparedStatement.setInt(1, reservation.getID_client());
			preparedStatement.setInt(2, reservation.getID_vehicle());
			preparedStatement.setDate( 3,Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate( 4,Date.valueOf(reservation.getFin()));
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
	
	public long delete(Reservation reservation) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_RESERVATION_QUERY);
			preparedStatement.setInt(1, reservation.getID_Reservation());
			preparedStatement.execute();
			connexion.close();
			preparedStatement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return reservation.getID_Reservation();
	}

	public List<Reservation> findResaById(long resaId) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_BY_ID_QUERY);
			preparedStatement.setInt(1, (int) resaId);
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			List<Reservation> reservations = new ArrayList<>();
			while (resultSet.next()) {
				int client_id = resultSet.getInt("client_id");
				int vehicle_id = resultSet.getInt("vehicle_id");
				LocalDate debut = (resultSet.getDate("debut").toLocalDate());
				LocalDate fin = (resultSet.getDate("fin")).toLocalDate();
				Reservation resa = new Reservation(client_id,vehicle_id,debut,fin);
				reservations.add(resa);
			}
			resultSet.close();
			connexion.close();
			preparedStatement.close();
			return reservations;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			preparedStatement.setInt(1, (int) clientId);
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			List<Reservation> reservations = new ArrayList<>();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int vehicle_id = resultSet.getInt("vehicle_id");
				LocalDate debut = (resultSet.getDate("debut").toLocalDate());
				LocalDate fin = (resultSet.getDate("fin")).toLocalDate();
				Reservation resa = new Reservation(id,vehicle_id,debut,fin);
				reservations.add(resa);
			}
			resultSet.close();
			connexion.close();
			preparedStatement.close();
			return reservations;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			preparedStatement.setInt(1, (int) vehicleId);
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			List<Reservation> reservations = new ArrayList<>();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int client_id = resultSet.getInt("client_id");
				LocalDate debut = (resultSet.getDate("debut").toLocalDate());
				LocalDate fin = (resultSet.getDate("fin")).toLocalDate();
				Reservation resa = new Reservation(id,client_id,debut,fin);
				reservations.add(resa);
			}
			resultSet.close();
			connexion.close();
			preparedStatement.close();
			return reservations;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Reservation> findAll() throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_QUERY);
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			List<Reservation> reservations = new ArrayList<>();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int client_id = resultSet.getInt("client_id");
				int vehicle_id = resultSet.getInt("vehicle_id");
				LocalDate debut = (resultSet.getDate("debut")).toLocalDate();
				LocalDate fin = (resultSet.getDate("fin")).toLocalDate();
				Reservation resa = new Reservation(id,client_id,vehicle_id,debut,fin);
				reservations.add(resa);
			}
			resultSet.close();
			connexion.close();
			preparedStatement.close();
			return reservations;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
