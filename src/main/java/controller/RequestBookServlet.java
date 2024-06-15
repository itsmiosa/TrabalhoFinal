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

import dao.BookDAO;

@WebServlet("/RequestBookServlet")
public class RequestBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookDAO bookDAO;

    @Override
    public void init() {
        bookDAO = new BookDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Principal userPrincipal = request.getUserPrincipal();
        String username = userPrincipal != null ? userPrincipal.getName() : null;

        String isbn = request.getParameter("isbn");

        try {
            Book book = bookDAO.requestBook(username, isbn);
            if (book != null) {
                request.setAttribute("title", book.getTitle());
                request.setAttribute("author", book.getAuthor());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
        }

        request.setAttribute("isbn", isbn);

        RequestDispatcher dispatcher;
        if (request.getAttribute("errorMessage") != null) {
            dispatcher = request.getRequestDispatcher("error.jsp");
        } else {
            dispatcher = request.getRequestDispatcher("confirmation.jsp");
        }
        dispatcher.forward(request, response);
    }
}
