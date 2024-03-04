package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import java.util.Scanner;

public class CreateVehicleCommand implements Command {
    private VehicleService vehicleService;

    public CreateVehicleCommand(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    public void execute() throws ServiceException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le constructeur du vehicule : ");
        String constructeur = scanner.nextLine();
        System.out.println("Entrez le modèle du vehicule : ");
        String model = scanner.nextLine();
        System.out.println("Entrez le nb de place du vehicule: ");
        int nb_place = Integer.parseInt(scanner.nextLine());

        Vehicle vehicle = new Vehicle();
        vehicle.setConstructeur(constructeur);
        vehicle.setModel(model);
        vehicle.setNb_place(nb_place);

        int id = vehicleService.create(vehicle);
        System.out.println("Vehicule créé avec l'identifiant : " + id);
    }
}

