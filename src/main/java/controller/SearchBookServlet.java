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
import java.util.List;

import dao.BookDAO;

@WebServlet("/SearchBookServlet")
public class SearchBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookDAO bookDAO;

    @Override
    public void init() {
        bookDAO = new BookDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookQuery = request.getParameter("bookQuery");
        response.setContentType("text/html;charset=UTF-8");

        Principal userPrincipal = request.getUserPrincipal();
        boolean isLoggedIn = userPrincipal != null;

        try {
            List<Book> books = bookDAO.searchBooksByTitle(bookQuery);
            request.setAttribute("books", books);

            // Forward to the appropriate JSP page based on login status
            RequestDispatcher dispatcher;
            if (isLoggedIn) {
                dispatcher = request.getRequestDispatcher("showBook.jsp");
            } else {
                dispatcher = request.getRequestDispatcher("showBookNoLogin.jsp");
            }
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error searching for books");
        }
    }
}
