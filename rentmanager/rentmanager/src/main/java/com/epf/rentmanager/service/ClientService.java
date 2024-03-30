package com.epf.rentmanager.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientDao clientDao;

    @Autowired
    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public long create(Client client) throws ServiceException {
        try {
            //VERIFICATION BACK NOM/PRENOM
            if (client.getNom().length() < 3 || client.getPrenom().length() < 3) {
                throw new ServiceException("Le nom et le prénom du client doivent avoir au moins 3 lettres.");
            }
            //VERIFICATION BACK AGE
            LocalDate datenaissance = client.getNaissance();
            LocalDate dateactuelle = LocalDate.now();
            long age = ChronoUnit.YEARS.between(datenaissance, dateactuelle);
            if (age < 18) {
                throw new ServiceException("Le client doit avoir au moin 18ans");
            }
            //VERIFICATION BACK MAIL
            String mail = client.getEmail();
            List<Client> clients = clientDao.findAll();
            for (Client allclient : clients) {
                if (allclient.getEmail().equals(mail)) {
                    throw new ServiceException("L'email est déjà utilisé");
                }
            }
            client.setNom(client.getNom().toUpperCase());
            return clientDao.create(client);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la création du client.");
        }
    }

    public long delete(Client client) throws ServiceException {
        try {
            return clientDao.delete(client);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la suppression du client.");
        }
    }

    public Client findById(int id) throws ServiceException {
        try {
            return clientDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche du client.");
        }
    }

    public List<Client> findAll() throws ServiceException {
        try {
            return clientDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche des clients.");
        }
    }

    public int count() throws ServiceException {
        try {
            return clientDao.count();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors du comptage des clients.");
        }
    }

    public void update(Client newClient) throws ServiceException {
        try {
            clientDao.update(newClient);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
