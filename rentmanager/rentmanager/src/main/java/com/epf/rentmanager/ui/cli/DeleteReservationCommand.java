package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ReservationService;

import java.util.Scanner;

public class DeleteReservationCommand {
    private ReservationService reservationService;

    public DeleteReservationCommand(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void execute() throws ServiceException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez l'id de la réservation que vous souhaitez supprimer : ");
        int id = Integer.parseInt(scanner.nextLine());
        reservationService.delete(reservationService.findResaById(id));
        System.out.println("Le client" + id + " à bien été supprimer ");
    }
}
