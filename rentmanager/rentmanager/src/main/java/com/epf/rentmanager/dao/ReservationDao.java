package com.epf.rentmanager.dao;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.models.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository // Annotation Spring indiquant que cette classe est un bean géré par Spring
public class ReservationDao {

	// Suppression de la méthode getInstance() car Spring gérera l'instanciation de ce bean

	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?)";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?";
	private static final String FIND_RESERVATIONS_BY_ID_QUERY = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(*) AS total FROM Reservation";

	public long create(Reservation reservation) throws DaoException {
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setInt(1, reservation.getID_client());
			preparedStatement.setInt(2, reservation.getID_vehicle());
			preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));
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

	public long delete(Reservation reservation) throws DaoException {
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_RESERVATION_QUERY)) {
			preparedStatement.setInt(1, reservation.getID_Reservation());
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return reservation.getID_Reservation();
	}

	public Reservation findResaById(long resaId) throws DaoException {
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_BY_ID_QUERY)) {
			preparedStatement.setLong(1, resaId);
			ResultSet resultSet = preparedStatement.executeQuery();
			int client_id = resultSet.getInt("client_id");
			int vehicle_id = resultSet.getInt("vehicle_id");
			LocalDate debut = resultSet.getDate("debut").toLocalDate();
			LocalDate fin = resultSet.getDate("fin").toLocalDate();
			Reservation reservation=new Reservation(client_id, vehicle_id, debut, fin);
			return reservation;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}

	}

	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY)) {
			preparedStatement.setLong(1, clientId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int vehicle_id = resultSet.getInt("vehicle_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				reservations.add(new Reservation(id, vehicle_id, debut, fin));
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return reservations;
	}

	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY)) {
			preparedStatement.setLong(1, vehicleId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int client_id = resultSet.getInt("client_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				reservations.add(new Reservation(id, client_id, debut, fin));
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return reservations;
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_QUERY)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int client_id = resultSet.getInt("client_id");
				int vehicle_id = resultSet.getInt("vehicle_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				reservations.add(new Reservation(id, client_id, vehicle_id, debut, fin));
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return reservations;
	}

	public int count() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(COUNT_RESERVATIONS_QUERY)) {
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
