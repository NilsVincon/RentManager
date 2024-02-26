package com.epf.rentmanager.ui.cli;
import com.epf.rentmanager.exception.CommandException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;


public class CreateReservationCommand {
    private ReservationService resaservice;

    public CreateReservationCommand (ReservationService resaservice) {
        this.resaservice = resaservice;
    }


    public void execute() throws CommandException {
        int id_client = Integer.parseInt("Entrez l'id du client : ");
        int id_vehicle = Integer.parseInt("Entrez l'id du vehicule : ");
        LocalDate debut = IOUtils.readDate("Entrez la date de début de la réservation (format dd/MM/yyyy) : ", true);
        LocalDate fin = IOUtils.readDate("Entrez la date de fin de la réservation (format dd/MM/yyyy) : ", true);
        Reservation reservation = new Reservation();
        reservation.setID_client(id_client);
        reservation.setID_vehicle(id_vehicle);
        reservation.setDebut(debut);
        reservation.setFin(fin);

        try {
            resaservice.create(reservation);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        System.out.println("La réservation a bien été créé.");
    }

}
