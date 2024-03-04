package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

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
    private ReservationService resevationService;

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
            List <Reservation> reservations = resevationService.findResaByClientId(clientId);
            System.out.println("Liste des reservations Servlet : "+reservations);
            List<Vehicle> vehicles=new ArrayList<>();
            Set<Integer> uniqueVehicles = new HashSet<>();
            for (Reservation reservation : reservations) {
                Vehicle vehicle = vehicleService.findById(reservation.getID_vehicle());
                vehicles.add(vehicle);
                reservation.setVehicleName(vehicle.getConstructeur(),vehicle.getModel());
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

}
