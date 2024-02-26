package com.epf.rentmanager.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epf.rentmanager.models.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.exception.ServiceException;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VehicleService vehicleService;
    public VehicleCreateServlet() {
        this.vehicleService = VehicleService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Affichage du formulaire de création de véhicules
        request.getRequestDispatcher("/WEB-INF/views/vehicle/create.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String constructeur = request.getParameter("constructeur");
        String model = request.getParameter("model");
        int nb_places = Integer.parseInt(request.getParameter("nb_places"));

        Vehicle newVehicle = new Vehicle();
        newVehicle.setConstructeur(constructeur);
        newVehicle.setModel(model);
        newVehicle.setNb_place(nb_places);

        try {
             vehicleService.create(newVehicle);
            response.sendRedirect(request.getContextPath() + "/cars");
        } catch (ServiceException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la création du véhicule.");
        }
    }

}
