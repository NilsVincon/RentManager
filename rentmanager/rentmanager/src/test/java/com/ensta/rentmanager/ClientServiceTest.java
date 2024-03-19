package com.ensta.rentmanager;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;

import java.time.LocalDate;

public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao clientDao;

    @BeforeEach
    void setUp() {
        // Initialisation des mocks
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.clientDao.findAll()).thenThrow(DaoException.class);
        // Then
        assertThrows(ServiceException.class, () -> clientService.findAll());
    }

    @Test
    public void create_should_fail_when_last_name_is_empty() throws DaoException {
        // Given
        Client client = new Client("","matthieu","matthieu@test.com", LocalDate.of(2002, 12, 28));
        when(this.clientDao.create(client)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.create(client));
    }

    @Test
    public void create_should_fail_when_first_name_is_empty() throws DaoException {
        // Given
        Client client = new Client("durant","","matthieu@test.com", LocalDate.of(2002, 12, 28));
        when(this.clientDao.create(client)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.create(client));
    }

    @Test
    public void findbyid_should_fail_when_id_doesnt_exist() throws DaoException {
        // Given

        when(this.clientDao.findById(66666)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.findById(66666));
    }

}

