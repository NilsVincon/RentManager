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

@WebServlet("/vehicles/update")
public class VehicleUpdateServlet extends HttpServlet {
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
        int ID_vehicle = Integer.parseInt(request.getParameter("id_vehicle"));
        Vehicle vehicle = null;
        String nbplaceError = request.getParameter("nbplaceError");
        request.setAttribute("nbplaceError", nbplaceError);
        try {
            vehicle = vehicleService.findById(ID_vehicle);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("vehicle",vehicle);
        request.getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String constructeur = request.getParameter("constructeur");
        String from_rents_create = (request.getParameter("from_rents_create"));
        System.out.println("fromVehicleCreate : "+from_rents_create);
        String model = request.getParameter("model");
        int nb_places = Integer.parseInt(request.getParameter("nb_places"));
        System.out.println(constructeur);
        System.out.println(model);
        System.out.println(nb_places);
        int ID_Vehicle = Integer.parseInt(request.getParameter("id_vehicle"));
        Vehicle newVehicle = new Vehicle();
        newVehicle.setID_vehicle(ID_Vehicle);
        newVehicle.setConstructeur(constructeur);
        newVehicle.setModel(model);
        newVehicle.setNb_place(nb_places);

        //VERIFICATION NOMBRE PLACE ENTRE 2 ET 9
        if ((nb_places<2)||(nb_places>9)){
            response.sendRedirect(request.getContextPath() + "/vehicles/update?id_vehicle=" + ID_Vehicle + "&nbplaceError=true");
            return;
        }

        try {
            vehicleService.update(newVehicle);
            response.sendRedirect(request.getContextPath() + "/vehicles/list");
        } catch (ServiceException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la modification du véhicule.");
        }
    }

}
