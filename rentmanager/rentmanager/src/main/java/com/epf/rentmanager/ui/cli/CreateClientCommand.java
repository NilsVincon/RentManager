package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.CommandException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;

public class CreateClientCommand implements Command {
    private ClientService clientService;

    public CreateClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() throws CommandException {
        String nom = IOUtils.readString("Entrez le nom du client : ", true);
        String prenom = IOUtils.readString("Entrez le prénom du client : ", true);
        String mail = IOUtils.readString("Entrez le mail du client : ", true);
        if (!mail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new CommandException("Le format de l'email est invalide.");
        }
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
