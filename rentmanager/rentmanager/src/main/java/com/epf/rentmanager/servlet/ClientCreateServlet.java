package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("last_name");
        String prenom = request.getParameter("first_name");
        String mail = request.getParameter("email");
        String naissance = request.getParameter("naissance");
        LocalDate datenaissance = null;
        if (naissance != null && !naissance.isEmpty()) {
            try {
                datenaissance = LocalDate.parse(naissance, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        }
        //VERIFICATION NOM ET PRENOM

        if (nom.length() < 3 || prenom.length() < 3) {
            request.setAttribute("nameError", true);
            request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
            return;
        }

        //VERIFICATION AGE

        LocalDate dateactuelle = LocalDate.now();
        long age = ChronoUnit.YEARS.between(datenaissance, dateactuelle);
        if (age < 18) {
            request.setAttribute("ageError", true);
            request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
            return;
        }

        //VERFICATION MAIL

        try {
            List<Client> clients = clientService.findAll();
            for (Client client : clients) {
                if (client.getEmail().equals(mail)) {
                    request.setAttribute("mailError", true);
                    request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
                    return;
                }
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        Client newClient = new Client();
        newClient.setNom(nom);
        newClient.setPrenom(prenom);
        newClient.setEmail(mail);
        newClient.setNaissance(datenaissance);
        String from_rents_create = (request.getParameter("from_rents_create"));

        try {
            clientService.create(newClient);
            if ( from_rents_create!= null && Objects.equals(from_rents_create, "true")) {
                response.sendRedirect(request.getContextPath() + "/rents/create?newClient_name=true");
            } else {
                response.sendRedirect(request.getContextPath() + "/users/list");
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la création du véhicule.");
        }
    }

}
