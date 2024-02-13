package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.CommandException;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.exception.ServiceException;

import java.time.LocalDate;
import java.util.Scanner;

public class CreateClientCommand implements Command {
    private ClientService clientService;

    public CreateClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() throws CommandException {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Entrez le nom du client : ");
            String nom = scanner.nextLine();
            System.out.println("Entrez le prénom du client : ");
            String prenom = scanner.nextLine();
            System.out.println("Entrez le mail du client : ");
            String mail = scanner.nextLine();
            System.out.println("Entrez la date de naissance du client : ");
            LocalDate naissance = LocalDate.parse(scanner.nextLine());
            if(nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() ) {
                throw new CommandException("Veuillez saisir toutes les informations requises.");
            }
            Client client = new Client();
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setEmail(mail);
            client.setNaissance(naissance);
            
        long id = clientService.create(client);
        System.out.println("Client créé avec l'identifiant : " + id);
    }
}
