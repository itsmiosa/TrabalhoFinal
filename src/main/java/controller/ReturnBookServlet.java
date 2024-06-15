package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.*;


import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

import dao.BookDAO;

@WebServlet("/ReturnBookServlet")
public class ReturnBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookDAO bookDAO;

    @Override
    public void init() {
        bookDAO = new BookDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Principal userPrincipal = request.getUserPrincipal();
        String username = userPrincipal.getName();

        try {
            List<BookRequest> requests = bookDAO.getUserRequests(username);
            request.setAttribute("requests", requests);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/returnBook.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String isbn = request.getParameter("isbn");

        try {
            bookDAO.returnBook(isbn);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
        }

        doGet(request, response);
    }
}
