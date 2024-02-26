package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Vehicle;
import com.epf.rentmanager.service.VehicleService;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ListVehicleCommand {
    private VehicleService vehicleService;

    public ListVehicleCommand(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public void execute() throws ServiceException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voulez vous afficher tous les clients ? : ");
        String reponse = scanner.nextLine();
        if (Objects.equals(reponse, "oui")){
            List<Vehicle> vehicles = vehicleService.findAll();
            for (Vehicle vehicle : vehicles) {
                System.out.println(vehicle);
            }
        }
    }
}
