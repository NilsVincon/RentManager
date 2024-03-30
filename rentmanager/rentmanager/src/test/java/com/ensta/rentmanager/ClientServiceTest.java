package com.ensta.rentmanager;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao clientDao;

    @BeforeEach
    void setUp() {
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
    public void create_should_succeed_with_valid_data() throws ServiceException, DaoException {
        // Given
        Client client = new Client("Durant", "Julie", "julie.durant@test.com", LocalDate.of(2002, 12, 28));
        when(clientDao.findAll()).thenReturn(new ArrayList<>());
        // When
        when(clientDao.create(client)).thenReturn(1L);
        long clientId = clientService.create(client);
        // Then
        assertEquals(1L, clientId);
    }

    @Test
    public void create_should_fail_when_last_name_is_too_short() {
        // Given
        Client client = new Client("Du", "Julie", "julie.durant@test.com", LocalDate.of(2002, 12, 28));

        // When & Then
        assertThrows(ServiceException.class, () -> clientService.create(client));
    }

    @Test
    public void create_should_fail_when_first_name_is_too_short() throws DaoException {
        // Given
        Client client = new Client("durant", "ju", "julie.durant@test.com", LocalDate.of(2002, 12, 28));
        when(this.clientDao.create(client)).thenThrow(DaoException.class);
        // Then
        assertThrows(ServiceException.class, () -> clientService.create(client));
    }

    @Test
    public void create_should_fail_when_underage() {
        // Given
        Client client = new Client("Durant", "Julie", "julie.durant@test.com", LocalDate.of(2010, 12, 28));
        // When & Then
        assertThrows(ServiceException.class, () -> clientService.create(client));
    }

    @Test
    public void create_should_fail_when_email_already_exists() throws DaoException {
        // Given
        List<Client> existingClients = new ArrayList<>();
        existingClients.add(new Client("Smith", "John", "john.smith@test.com", LocalDate.of(1990, 1, 1)));
        when(clientDao.findAll()).thenReturn(existingClients);
        Client client = new Client("Durant", "Julie", "john.smith@test.com", LocalDate.of(2000, 12, 28));
        // When & Then
        assertThrows(ServiceException.class, () -> clientService.create(client));
    }

}

