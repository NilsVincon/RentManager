package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.exception.ServiceException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ListClientCommand implements Command {
    private ClientService clientService;

    public ListClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() throws ServiceException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voulez vous afficher tous les clients ? : ");
        String reponse = scanner.nextLine();
        if (Objects.equals(reponse, "oui")){
            List<Client> clients = clientService.findAll();
            for (Client client : clients) {
                System.out.println(client);
            }
        }
    }
}