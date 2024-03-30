package com.epf.rentmanager.service;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    @Autowired
    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public long create(Reservation reservation) throws ServiceException {
        try {
            //VERIFICATION BACK DATE 7 JOURS MAX
            LocalDate debut = reservation.getDebut();
            LocalDate fin = reservation.getFin();
            if (debut.plusDays(7).isBefore(fin)) {
                throw new ServiceException("Une reservation ne peut pas durer plus de 7 jours");
            }
            //VERIFICATION BACK DEBUT AVANT FIN
            if (fin.isBefore(debut)) {
                throw new ServiceException("Le début ne peut pas être après la fin");
            }
            //VERIFICATION BACK POUR DEJA RESERVE ET 30J MAX PAS REALISE CAR TROP LONGUE MAIS MEME PRINCIPE QUE LES 2 PREMIERS
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

    public List<Reservation> findResaByClientId(int id) throws ServiceException {
        try {
            return reservationDao.findResaByClientId(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche de la réservation.");
        }
    }

    public List<Reservation> findResaByVehicleId(int id) throws ServiceException {
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

    public void update(Reservation newResa) throws ServiceException {
        try {
            reservationDao.update(newResa);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
