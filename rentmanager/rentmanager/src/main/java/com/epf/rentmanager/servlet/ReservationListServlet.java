package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/rents/list")
public class ReservationListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Reservation> rents = reservationService.findAll();
            List<String> noms_clients = new ArrayList<>();
            List<String> prenoms_clients = new ArrayList<>();
            List<String> constucteurs_vehciles = new ArrayList<>();
            List<String> models_vehicles = new ArrayList<>();
            for (Reservation reservation : rents) {
                String nom = (clientService.findById(reservation.getID_client())).getNom();
                noms_clients.add(nom);
                String prenom = (clientService.findById(reservation.getID_client())).getPrenom();
                prenoms_clients.add(prenom);
                String constructeur = (vehicleService.findById(reservation.getID_client())).getConstructeur();
                constucteurs_vehciles.add(constructeur);
                String model = (vehicleService.findById(reservation.getID_client())).getModel();
                models_vehicles.add(model);

            }
            request.setAttribute("rents", rents);
            request.setAttribute("nom", noms_clients);
            request.setAttribute("prenom", prenoms_clients);
            request.setAttribute("constructeur", constucteurs_vehciles);
            request.setAttribute("model", models_vehicles);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int reservationId = Integer.parseInt(request.getParameter("reservationId"));
        try {
            reservationService.delete(reservationService.findResaById(reservationId));
            System.out.println("resa"+reservationId+"supprimé!");
            request.setAttribute("successMessage", "La suppression de la reservatopn : "+reservationId+" a été effectuée avec succès !");
            response.sendRedirect(request.getContextPath() + "/rents/list");
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la suppression de la reservation.");
            throw new RuntimeException(e);
        }
    }
}
