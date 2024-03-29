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
        int id = Integer.parseInt(scanner.nextLine());
        reservationService.delete(reservationService.findResaById(id));
    }
}
