package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.models.Reservation;
import com.epf.rentmanager.models.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
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
            System.out.println(rentsvehicles);
            List<Client> rentsusers = clientService.findAll();
            request.setAttribute("rentsusers", rentsusers);
            request.setAttribute("rentsvehicles", rentsvehicles);
            String vehicleCreated = request.getParameter("vehicleCreated");
            if ("true".equals(vehicleCreated)) {
                request.setAttribute("newVehicleCreated", true);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ID_Client = Integer.parseInt((request.getParameter("client")));
        int ID_Vehicle = Integer.parseInt((request.getParameter("vehicle")));
        LocalDate debut = null;
        LocalDate fin = null;
        String debutParam = request.getParameter("debut");
        String finParam = request.getParameter("fin");
        if (debutParam != null && finParam != null) {
            debut = LocalDate.parse(debutParam, DateTimeFormatter.ofPattern("dd/MM/yyyy"));;
            fin =   LocalDate.parse(finParam, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        System.out.println(debutParam);
        System.out.println(finParam);
        Reservation newResa = new Reservation();
        newResa.setID_client(ID_Client);
        newResa.setID_vehicle(ID_Vehicle);
        newResa.setDebut(debut);
        newResa.setFin(fin);

        try {
            reservationService.create(newResa);
            response.sendRedirect(request.getContextPath() + "/rents/list");
        } catch (ServiceException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la création du véhicule.");
        }
    }

}
