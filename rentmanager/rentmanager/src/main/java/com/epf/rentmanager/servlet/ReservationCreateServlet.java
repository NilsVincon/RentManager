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
            System.out.println("Je suis a ");
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
        System.out.println("client : "+ID_Client);
        System.out.println("vehicle : "+ID_Vehicle);
        LocalDate debut = null;
        LocalDate fin = null;
        String debutParam = request.getParameter("debut");
        String finParam = request.getParameter("fin");
        if (debutParam != null && finParam != null) {
            debut = LocalDate.parse(debutParam, DateTimeFormatter.ofPattern("dd/MM/yyyy"));;
            fin =   LocalDate.parse(finParam, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            //VERIFICATION DATE 7 JOURS MAX
            if (debut.plusDays(7).isBefore(fin)) {
                request.setAttribute("dateError", true);
                doGet(request, response);
                return;
            }
            //VERIFICATION DEBUT AVANT FIN
            if (fin.isBefore(debut)) {
                request.setAttribute("datefinError", true);
                doGet(request, response);
                return;
            }
        }
        List<Reservation> reservations= null;
        try {
            reservations = reservationService.findResaByVehicleId(ID_Vehicle);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        for (Reservation reservation : reservations) {
            if (((debut.isAfter(reservation.getDebut()) || debut.isEqual(reservation.getDebut())) && debut.isBefore(reservation.getFin()) || debut.isEqual(reservation.getFin()))||((fin.isAfter(reservation.getDebut()) || fin.isEqual(reservation.getDebut())) && fin.isBefore(reservation.getFin()) || fin.isEqual(reservation.getFin())))
                  {
                request.setAttribute("periodError", true);
                doGet(request, response);
                return;
            }
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
            System.out.println(newResa);
            response.sendRedirect(request.getContextPath() + "/rents/list");
        } catch (ServiceException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la création du véhicule.");
        }
    }

}
