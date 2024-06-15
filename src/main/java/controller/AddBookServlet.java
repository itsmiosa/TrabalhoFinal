package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Book;


import java.io.IOException;

import dao.BookDAO;

@WebServlet("/TrabalhoFinal/AddBookServlet")
public class AddBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookDAO bookDAO;

    @Override
    public void init() {
        bookDAO = new BookDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbn = request.getParameter("isbn");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String synopsis = request.getParameter("abstract");
        String genre = request.getParameter("genre");
        int numberOfCopies = Integer.parseInt(request.getParameter("numberOfCopies"));
        
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setAuthor(author);
        book.setSynopsis(synopsis);
        book.setGenre(genre);
        book.setAvailable(numberOfCopies > 0);
        book.setNumberOfCopies(numberOfCopies);

        try {
            bookDAO.addBook(book);
            request.getRequestDispatcher("homepage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding book");
        }
    }
}
