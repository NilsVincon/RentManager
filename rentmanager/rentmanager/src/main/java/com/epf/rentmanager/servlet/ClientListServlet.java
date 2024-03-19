package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/users/list")
public class ClientListServlet extends HttpServlet {
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
        try {
            List<Client> users = clientService.findAll();
            request.setAttribute("users", users);
            System.out.println(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int clientID = Integer.parseInt(request.getParameter("clientID"));
        if (Objects.equals(action, "delete_client")) {
            String deleteornot = request.getParameter("deleteornot");
            if (Objects.equals(deleteornot, "true")) {
                String prenom = request.getParameter("prenom");
                String nom = request.getParameter("nom");
                try {
                    clientService.delete(clientService.findById(clientID));
                    request.setAttribute("successMessage", "La suppression du client : " + prenom + " " + nom + " a été effectuée avec succès !");
                    response.sendRedirect(request.getContextPath() + "/users/list");
                } catch (ServiceException e) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la suppression du véhicule.");
                    throw new RuntimeException(e);
                }
            }
            else {
                System.out.println("false");
                doGet(request,response);
            }
        }
        if (Objects.equals(action, "modif_client")) {
            System.out.println("modif_client");

        }
        if (Objects.equals(action, "dont_delete_client")) {
            doGet(request,response);
        }


    }
}
