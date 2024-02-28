package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.utils.IOUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Interface {

    private final ClientService clientService;

    @Autowired
    public Interface(ClientService clientService) {
        this.clientService = clientService;
    }

    public void displayMainMenu() {
        boolean running = true;
        while (running) {
            IOUtils.print("\n### Menu principal ###");
            IOUtils.print("1. Créer un client");
            IOUtils.print("12. Quitter");

            int choice = IOUtils.readInt("Choisissez une option : ");

            switch (choice) {
                case 1:
                    createClient();
                    break;
                case 12:
                    running = false;
                    break;
                default:
                    IOUtils.print("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private void createClient() {

        String nom = IOUtils.readString("Entrez le nom du client : ", true);
        String prenom = IOUtils.readString("Entrez le prénom du client : ", true);
        String mail = IOUtils.readString("Entrez le mail du client : ", true);
        LocalDate naissance = IOUtils.readDate("Entrez la date de naissance du client (format dd/MM/yyyy) : ", true);

        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setEmail(mail);
        client.setNaissance(naissance);

        try {
            clientService.create(client);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Le client " + nom + " a bien été créé.");
    }
}
