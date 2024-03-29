package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import java.util.Scanner;

public class DeleteClientCommand implements Command{
    private ClientService clientService;

    public DeleteClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() throws ServiceException {
            Scanner scanner = new Scanner(System.in);
            int id = Integer.parseInt(scanner.nextLine());
            clientService.delete(clientService.findById(id));
    }
}