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
import java.util.stream.Collectors;

@WebServlet("/users/update")
public class ClientUpdateServlet extends HttpServlet {
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
        int ID_Client = Integer.parseInt(request.getParameter("client_id"));
        String nameError = request.getParameter("nameError");
        String ageError = request.getParameter("ageError");
        request.setAttribute("nameError", nameError);
        request.setAttribute("ageError", ageError);
        try {
            Client client = clientService.findById(ID_Client);
            request.setAttribute("client",client);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("/WEB-INF/views/users/update.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("last_name");
        String prenom = request.getParameter("first_name");
        String mail = request.getParameter("email");
        String naissance = request.getParameter("naissance");
        int ID_Client = Integer.parseInt(request.getParameter("id_client"));
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
            response.sendRedirect(request.getContextPath() + "/users/update?client_id=" + ID_Client + "&nameError=true");
            return;
        }

        //VERIFICATION AGE

        LocalDate dateactuelle = LocalDate.now();
        long age = ChronoUnit.YEARS.between(datenaissance, dateactuelle);
        if (age < 18) {
            response.sendRedirect(request.getContextPath() + "/users/update?client_id=" + ID_Client + "&ageError=true");
            return;
        }
        //VERFICATION MAIL

        try {
            List<Client> clients = clientService.findAll();
            System.out.println("clients1: " + clients);

            // Filtrer les clients ayant le même e-mail que celui fourni dans la variable 'mail'
            clients = clients.stream()
                    .filter(client -> client.getEmail().equals(mail))
                    .collect(Collectors.toList());

            System.out.println("clients2: " + clients);

            // Vérifier s'il y a des clients filtrés (c'est-à-dire s'il y a des clients avec le même e-mail)
            if (!clients.isEmpty()) {
                // Si un client avec le même e-mail existe déjà, effectuez la redirection
                response.sendRedirect(request.getContextPath() + "/users/create?nom=" + nom + "&prenom=" + prenom + "&naissance=" + datenaissance + "&mailError=true");
                return;
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }


        Client newClient = new Client();
        newClient.setID_client(ID_Client);
        newClient.setNom(nom);
        newClient.setPrenom(prenom);
        newClient.setEmail(mail);
        newClient.setNaissance(datenaissance);

        try {
            clientService.update(newClient);
            response.sendRedirect(request.getContextPath() + "/users/list");

        } catch (ServiceException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la modification du client.");
        }
    }

}
