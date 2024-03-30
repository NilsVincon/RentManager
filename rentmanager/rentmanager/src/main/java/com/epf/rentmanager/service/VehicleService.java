package com.epf.rentmanager.service;

import java.sql.SQLException;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    private final VehicleDao vehicleDao;

    @Autowired
    public VehicleService(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    public int create(Vehicle vehicle) throws ServiceException {
        try {
            //VERIFICATION BACK CONSTRUCTEUR NON VIDE
            if (vehicle.getConstructeur().isEmpty()) {
                throw new ServiceException("Le véhicule doit avoir un constructeur.");
            }
            //VERIFICATION BACK MODEL NON VIDE
            if (vehicle.getModel().isEmpty()) {
                throw new ServiceException("Le véhicule doit avoir un model.");
            }
            //VERIFICATION BACK NOMBRE PLACE ENTRE 2 ET 9
            if ((vehicle.getNb_place() < 2) || (vehicle.getNb_place() > 9)) {
                throw new ServiceException("Le véhicule doit avoir entre 2 et 9 places");
            }
            return vehicleDao.create(vehicle);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la création du véhicule.");
        }
    }

    public long delete(Vehicle vehicle) throws ServiceException {
        try {
            return vehicleDao.delete(vehicle);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la suppression du véhicule.");
        }
    }

    public Vehicle findById(int id) throws ServiceException {
        try {
            return vehicleDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche du véhicule.");
        }
    }

    public List<Vehicle> findAll() throws ServiceException {
        try {
            return vehicleDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche des véhicules.");
        }
    }

    public int count() throws ServiceException {
        try {
            return vehicleDao.count();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors du comptage des véhicules.");
        }
    }

    public void update(Vehicle newVehicle) throws ServiceException {
        try {
            vehicleDao.update(newVehicle);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
