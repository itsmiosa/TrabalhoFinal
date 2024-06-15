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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/TrabalhoFinal/NewAddUserServlet")
public class NewAddUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String nif = request.getParameter("nif");
        String roles = request.getParameter("role");
        

        try {
            password = PassHash.generateHash(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = new User(username, password, email, nif);
        
        List<Role> roleList = new ArrayList<>();
        
        Role role1 = new Role("user");
        roleList.add(role1);
        
        if(roles != null) {
        	Role role2 = new Role("admin");
        	roleList.add(role2);
        }
        
        
        
        UserDAO userDAO = new UserDAO();
        try {
            userDAO.addUserAndRoles(user, roleList);
            request.getRequestDispatcher("homepage.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
           
        }
    }
}
