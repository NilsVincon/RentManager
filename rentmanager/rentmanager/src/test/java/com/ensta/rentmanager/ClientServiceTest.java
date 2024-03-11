package com.ensta.rentmanager;

import com.epf.rentmanager.exception.ServiceException;
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
}

