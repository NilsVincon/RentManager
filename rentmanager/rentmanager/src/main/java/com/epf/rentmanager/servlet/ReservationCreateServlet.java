package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.models.Reservation;
import com.epf.rentmanager.models.Vehicle;
import com.epf.rentmanager.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Vehicle> rentsvehicles = vehicleService.findAll();
            List<Client> rentsusers = clientService.findAll();
            request.setAttribute("rentsusers", rentsusers);
            request.setAttribute("rentsvehicles", rentsvehicles);
            String vehicleCreated = request.getParameter("vehicleCreated");
            if ("true".equals(vehicleCreated)) {
                request.setAttribute("newVehicleCreated", true);
            }
            String id_client = request.getParameter("id_client");
            request.setAttribute("id_client", id_client);
            String id_vehicle = request.getParameter("id_vehicle");
            request.setAttribute("id_vehicle", id_vehicle);
            String dateError = request.getParameter("dateError");
            request.setAttribute("dateError", dateError);
            String datefinError = request.getParameter("datefinError");
            request.setAttribute("datefinError", datefinError);
            String periodError = request.getParameter("periodError");
            request.setAttribute("periodError", periodError);
            String trente_daysError = request.getParameter("trente_daysError");
            request.setAttribute("trente_daysError", trente_daysError);

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }

    Comparator<Reservation> comparator = new Comparator<Reservation>() {
        @Override
        public int compare(Reservation r1, Reservation r2) {
            return r1.getDebut().compareTo(r2.getDebut());
        }
    };

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ID_Client = Integer.parseInt((request.getParameter("client")));
        int ID_Vehicle = Integer.parseInt((request.getParameter("vehicle")));
        LocalDate debut = null;
        LocalDate fin = null;
        String debutParam = request.getParameter("debut");
        String finParam = request.getParameter("fin");
        if (debutParam != null && finParam != null) {
            debut = LocalDate.parse(debutParam, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            fin = LocalDate.parse(finParam, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            //VERIFICATION DATE 7 JOURS MAX
            if (debut.plusDays(7).isBefore(fin)) {
                response.sendRedirect(request.getContextPath() + "/rents/create?id_client=" + ID_Client + "&id_vehicle=" + ID_Vehicle + "&dateError=true");
                return;
            }
            //VERIFICATION DEBUT AVANT FIN
            if (fin.isBefore(debut)) {
                response.sendRedirect(request.getContextPath() + "/rents/create?id_client=" + ID_Client + "&id_vehicle=" + ID_Vehicle + "&datefinError=true");
                return;
            }
        }
        List<Reservation> reservations = null;
        try {
            reservations = reservationService.findResaByVehicleId(ID_Vehicle);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        for (Reservation reservation : reservations) {

            //VERIFICATION VEHICULE DEJA RESERVE
            if (((debut.isAfter(reservation.getDebut()) || debut.isEqual(reservation.getDebut())) && debut.isBefore(reservation.getFin()) || debut.isEqual(reservation.getFin())) || ((fin.isAfter(reservation.getDebut()) || fin.isEqual(reservation.getDebut())) && fin.isBefore(reservation.getFin()) || fin.isEqual(reservation.getFin()))) {
                response.sendRedirect(request.getContextPath() + "/rents/create?id_client=" + ID_Client + "&id_vehicle=" + ID_Vehicle + "&periodError=true");
                return;
            }
        }
        Reservation newResa = new Reservation();
        newResa.setID_client(ID_Client);
        newResa.setID_vehicle(ID_Vehicle);
        newResa.setDebut(debut);
        newResa.setFin(fin);
        reservations.add(newResa);
        Collections.sort(reservations, comparator);
        long period_en_cours = ChronoUnit.DAYS.between(reservations.get(0).getDebut(), reservations.get(0).getFin()) + 1;
        for (int i = 0; i < reservations.size() - 1; i++) {
            Reservation currentReservation = reservations.get(i);
            Reservation nextReservation = reservations.get(i + 1);
            //VERIFICATION VEHICULE RESERVER 30 JOURS DE SUITE
            long daysBetween = ChronoUnit.DAYS.between(currentReservation.getFin(), nextReservation.getDebut());
            if (daysBetween == 1) {
                period_en_cours += ChronoUnit.DAYS.between(nextReservation.getDebut(), nextReservation.getFin()) + 1;
            }
        }
        if (period_en_cours >= 30) {
            response.sendRedirect(request.getContextPath() + "/rents/create?id_client=" + ID_Client + "&id_vehicle=" + ID_Vehicle + "&trente_daysError=true");
            return;
        }
        try {
            reservationService.create(newResa);
            response.sendRedirect(request.getContextPath() + "/rents/list");
        } catch (ServiceException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la création de la reservation.");
        }
    }

}
