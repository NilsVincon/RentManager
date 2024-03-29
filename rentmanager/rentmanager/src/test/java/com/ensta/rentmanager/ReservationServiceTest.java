package com.ensta.rentmanager;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Reservation;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


public class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationDao reservationDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.reservationDao.findAll()).thenThrow(DaoException.class);
        // Then
        assertThrows(ServiceException.class, () -> reservationService.findAll());
    }

    @Test
    public void create_should_fail_when_last_name_is_empty() throws DaoException {
        // Given
        Reservation reservation = new Reservation(1, 2, LocalDate.of(2024, 3, 27), LocalDate.of(2024, 3, 30));
        when(this.reservationDao.create(reservation)).thenThrow(DaoException.class);
        // Then
        assertThrows(ServiceException.class, () -> reservationService.create(reservation));
    }

    @Test
    public void create_should_fail_when_first_name_is_empty() throws DaoException {
        // Given
        Reservation reservation = new Reservation(1, 2, LocalDate.of(2024, 3, 27), LocalDate.of(2024, 3, 30));
        when(this.reservationDao.create(reservation)).thenThrow(DaoException.class);
        // Then
        assertThrows(ServiceException.class, () -> reservationService.create(reservation));
    }

    @Test
    public void findbyid_should_fail_when_id_resa_doesnt_exist() throws DaoException {
        // Given
        when(this.reservationDao.findResaById(66666)).thenThrow(DaoException.class);
        // Then
        assertThrows(ServiceException.class, () -> reservationService.findResaById(66666));
    }

    @Test
    public void findbyid_should_fail_when_id_vehicle_doesnt_exist() throws DaoException {
        // Given
        when(this.reservationDao.findResaByVehicleId(66666)).thenThrow(DaoException.class);
        // Then
        assertThrows(ServiceException.class, () -> reservationService.findResaByVehicleId(66666));
    }

    @Test
    public void findbyid_should_fail_when_id_client_doesnt_exist() throws DaoException {
        // Given
        when(this.reservationDao.findResaByClientId(66666)).thenThrow(DaoException.class);
        // Then
        assertThrows(ServiceException.class, () -> reservationService.findResaByClientId(66666));
    }


}

