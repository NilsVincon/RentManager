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
        String constructeur = scanner.nextLine();
        String model = scanner.nextLine();
        int nb_place = Integer.parseInt(scanner.nextLine());

        Vehicle vehicle = new Vehicle();
        vehicle.setConstructeur(constructeur);
        vehicle.setModel(model);
        vehicle.setNb_place(nb_place);

        int id = vehicleService.create(vehicle);
    }
}

