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
        String mailError = request.getParameter("mailError");
        request.setAttribute("mailError", mailError);
        String ageError = request.getParameter("ageError");
        request.setAttribute("ageError", ageError);
        String nameError = request.getParameter("nameError");
        request.setAttribute("nameError", nameError);
        String nom = request.getParameter("nom");
        request.setAttribute("nom", nom);
        String prenom = request.getParameter("prenom");
        request.setAttribute("prenom", prenom);
        String mail = request.getParameter("mail");
        request.setAttribute("mail", mail);
        LocalDate naissance = null;
        String naissanceParam = request.getParameter("naissance");
        if (naissanceParam != null && !naissanceParam.isEmpty()) {
            try {
                naissance = LocalDate.parse(naissanceParam);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                request.setAttribute("naissanceError", "Format de date invalide. Utilisez le format AAAA-MM-JJ.");
            }
        }
        request.setAttribute("naissance", naissance);
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
        //VERIFICATION FRONT NOM/PRENOM
        if (nom.length() < 3 || prenom.length() < 3) {
            response.sendRedirect(request.getContextPath() + "/users/create?mail=" + mail + "&naissance=" + datenaissance + "&nameError=true");
            return;
        }
        //VERIFICATION FRONT AGE
        LocalDate dateactuelle = LocalDate.now();
        long age = ChronoUnit.YEARS.between(datenaissance, dateactuelle);
        if (age < 18) {
            response.sendRedirect(request.getContextPath() + "/users/create?nom=" + nom + "&prenom=" + prenom + "&mail=" + mail + "&ageError=true");
            return;
        }
        //VERFICATION FRONT MAIL
        try {
            List<Client> clients = clientService.findAll();
            for (Client client : clients) {
                if (client.getEmail().equals(mail)) {
                    response.sendRedirect(request.getContextPath() + "/users/create?nom=" + nom + "&prenom=" + prenom + "&naissance=" + datenaissance + "&mailError=true");
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
            if (from_rents_create != null && Objects.equals(from_rents_create, "true")) {
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
