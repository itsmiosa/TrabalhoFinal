package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.UserDAO;
import model.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet("/TrabalhoFinal/AddUserServlet")
public class AddUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String nif = request.getParameter("nif");

        try {
            password = PassHash.generateHash(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = new User(username, password, email, nif);
        Role role = new Role();
        role.setRole("user");
        
        
        UserDAO userDAO = new UserDAO();
        try {
            userDAO.addUserAndRole(user, role);
            request.getRequestDispatcher("homepage.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }
}
