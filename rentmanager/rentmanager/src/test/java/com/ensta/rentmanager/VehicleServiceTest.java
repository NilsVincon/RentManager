package com.ensta.rentmanager;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Vehicle;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


public class VehicleServiceTest {
    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleDao vehicleDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.vehicleDao.findAll()).thenThrow(DaoException.class);
        // Then
        assertThrows(ServiceException.class, () -> vehicleService.findAll());
    }

    @Test
    public void create_should_fail_when_constructor_is_empty() throws DaoException {
        // Given
        Vehicle vehicle = new Vehicle("", "cayman", 3);
        when(this.vehicleDao.create(vehicle)).thenThrow(DaoException.class);
        // Then
        assertThrows(ServiceException.class, () -> vehicleService.create(vehicle));
    }

    @Test
    public void create_should_fail_when_model_is_empty() throws DaoException {
        // Given
        Vehicle vehicle = new Vehicle("", "cayman", 3);
        when(this.vehicleDao.create(vehicle)).thenThrow(DaoException.class);
        // Then
        assertThrows(ServiceException.class, () -> vehicleService.create(vehicle));
    }

    @Test
    public void findbyid_should_fail_when_id_doesnt_exist() throws DaoException {
        // Given
        when(this.vehicleDao.findById(66666)).thenThrow(DaoException.class);
        // Then
        assertThrows(ServiceException.class, () -> vehicleService.findById(66666));
    }

}

