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
                try {
                    clientService.delete(clientService.findById(clientID));
                    response.sendRedirect(request.getContextPath() + "/users/list");
                } catch (ServiceException e) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la suppression du client.");
                    throw new RuntimeException(e);
                }
            } else {
                doGet(request, response);
            }
        }
        if (Objects.equals(action, "dont_delete_client")) {
            doGet(request, response);
        }
    }
}
