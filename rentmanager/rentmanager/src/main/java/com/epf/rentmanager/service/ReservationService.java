package com.epf.rentmanager.service;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Annotation Spring indiquant que cette classe est un bean géré par Spring
public class ReservationService {

    private final ReservationDao reservationDao;

    // Injection du ReservationDao via le constructeur
    @Autowired
    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public long create(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la création de la réservation.");
        }
    }

    public long delete(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.delete(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la suppression de la réservation.");
        }
    }

    public Reservation findResaById(int id) throws ServiceException {
        try {
            return reservationDao.findResaById(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche de la réservation.");
        }
    }

    public List<Reservation>  findResaByClientId(int id) throws ServiceException {
        try {
            return reservationDao.findResaByClientId(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche de la réservation.");
        }
    }

    public List<Reservation>  findResaByVehicleId(long id) throws ServiceException {
        try {
            return reservationDao.findResaByVehicleId(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche de la réservation.");
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche des réservations.");
        }
    }

    public int count() throws ServiceException {
        try {
            return reservationDao.count();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors du comptage des réservations.");
        }
    }
}
