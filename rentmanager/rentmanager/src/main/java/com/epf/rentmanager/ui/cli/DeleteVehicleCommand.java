package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import java.util.Scanner;

public class DeleteVehicleCommand implements Command{
    private VehicleService vehicleService;

    public DeleteVehicleCommand(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    public void execute() throws ServiceException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez l'id du vehicule que vous souhaitez supprimer : ");
        int id = Integer.parseInt(scanner.nextLine());
        vehicleService.delete(vehicleService.findById(id));
        System.out.println("Véhicule" + id + " à bien été supprimé ");


    }
}
