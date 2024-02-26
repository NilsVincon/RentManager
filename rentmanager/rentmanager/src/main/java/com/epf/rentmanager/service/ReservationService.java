package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Reservation;

import java.util.List;

public class ReservationService {
    private ReservationDao reservationDao;
    public static ReservationService instance;

    private ReservationService() {
        this.reservationDao = ReservationDao.getInstance();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }

        return instance;
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

    public Reservation findResaById(long id) throws ServiceException {
        try {
            return (Reservation) reservationDao.findResaById(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche de la réservation.");
        }
    }
    public Reservation findResaByClientId(long id) throws ServiceException {
        try {
            return (Reservation) reservationDao.findResaByClientId(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche de la réservation.");
        }
    }
    public Reservation findResaByVehicleId(long id) throws ServiceException {
        try {
            return (Reservation) reservationDao.findResaByVehicleId(id);
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

}


