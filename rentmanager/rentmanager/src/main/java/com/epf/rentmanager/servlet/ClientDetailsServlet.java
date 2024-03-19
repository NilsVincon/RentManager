package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.models.Reservation;
import com.epf.rentmanager.models.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/users/details")
public class ClientDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer url = request.getRequestURL();
        System.out.println("URL de la requête : " + url.toString());
        int clientId = Integer.parseInt(request.getParameter("client_id"));
        System.out.println(clientId);
        try {
            Client client = clientService.findById(clientId);
            List<Reservation> reservations = reservationService.findResaByClientId(clientId);
            System.out.println("Liste des reservations Servlet : " + reservations);
            List<Vehicle> vehicles = new ArrayList<>();
            Set<Integer> uniqueVehicles = new HashSet<>();
            for (Reservation reservation : reservations) {
                Vehicle vehicle = vehicleService.findById(reservation.getID_vehicle());
                vehicles.add(vehicle);
                reservation.setVehicleName(vehicle.getConstructeur(), vehicle.getModel());
                uniqueVehicles.add(vehicle.getID_vehicle());
            }
            int nb_vehicle_unique = uniqueVehicles.size();
            request.setAttribute("reservations", reservations);
            request.setAttribute("nb_vehicle_unique", nb_vehicle_unique);
            request.setAttribute("client", client);
            request.setAttribute("vehicles", vehicles);
            request.getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new ServletException("Erreur lors de la récupération des détails du client", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int clientId = Integer.parseInt(request.getParameter("client_id"));
        if (Objects.equals(action, "delete_reservation")) {
            int reservationId = Integer.parseInt(request.getParameter("reservationId"));
            try {
                reservationService.delete(reservationService.findResaById(reservationId));
                System.out.println("resa" + reservationId + "supprimé!");
                request.setAttribute("successMessage", "La suppression de la reservation : " + reservationId + " a été effectuée avec succès !");
                response.sendRedirect(request.getRequestURI() + "?client_id=" + clientId);
            } catch (ServiceException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la suppression de la reservation.");
                throw new RuntimeException(e);
            }
        }
        if (Objects.equals(action, "delete_vehicle")) {
            int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
            String constructeur = request.getParameter("constructeur");
            String model = request.getParameter("model");
            System.out.println(constructeur);

            try {
                vehicleService.delete(vehicleService.findById(vehicleId));
                request.setAttribute("successMessage", "La suppression du vehicule : " + constructeur + " " + model + " a été effectuée avec succès !");
                response.sendRedirect(request.getRequestURI() + "?client_id=" + clientId);
            } catch (ServiceException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la suppression du véhicule.");
                throw new RuntimeException(e);
            }
        }

    }
}
