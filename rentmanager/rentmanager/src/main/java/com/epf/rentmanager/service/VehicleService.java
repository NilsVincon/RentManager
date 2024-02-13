package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;

public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}
	
	
	public long create(Vehicle vehicle) throws ServiceException {
		try {
			if (vehicle.getConstructeur().isEmpty()) {
				throw new ServiceException("Le vehicule doit avoir un constructeur");
			}
			if (vehicle.getNb_place()<1) {
				throw new ServiceException("Le vehicule doit avoir au minimum 1 place");
			}
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création du vehicule.");
		}
	}

	public long delete(Vehicle vehicle) throws ServiceException{
		try {
			return vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la suppression du vehicule.");
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la recherche du vehicule.");
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la recherche des vehicules.");
		}
	}
	
}
