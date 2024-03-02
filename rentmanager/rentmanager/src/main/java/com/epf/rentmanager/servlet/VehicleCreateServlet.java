package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epf.rentmanager.models.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/vehicles/create")
public class VehicleCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String constructeur = request.getParameter("constructeur");
        String model = request.getParameter("model");
        int nb_places = Integer.parseInt(request.getParameter("nb_places"));
        System.out.println(constructeur);
        System.out.println(model);
        System.out.println(nb_places);
        Vehicle newVehicle = new Vehicle();
        newVehicle.setConstructeur(constructeur);
        newVehicle.setModel(model);
        newVehicle.setNb_place(nb_places);
        try {
            vehicleService.create(newVehicle);
            String fromVehicleCreate = request.getParameter("from_Rents");
            System.out.println(fromVehicleCreate);
            if ( fromVehicleCreate!= null && Objects.equals(fromVehicleCreate, "true")) {
                response.sendRedirect(request.getContextPath() + "/rents/create?newVehicleId=" + newVehicle.getID_vehicle());
            } else {
                response.sendRedirect(request.getContextPath() + "/vehicles/list");
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la création du véhicule.");
        }
    }

}
