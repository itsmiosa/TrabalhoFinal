package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/TrabalhoFinal/AddBookServlet")
public class AddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String JDBC_URL = "jdbc:h2:tcp://localhost/C:\\Users\\migue\\Desktop\\Faculdade\\Semestre 6\\SCDist\\h2-2023-09-17\\scdistdb";
	private static final String JDBC_USER = "scdist";
	private static final String JDBC_PASSWORD = "scdist";

       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbn = request.getParameter("isbn");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String synopsis = request.getParameter("abstract");
        String genre = request.getParameter("genre");
        int numberCopies = Integer.parseInt(request.getParameter("numberOfCopies"));
        
        Connection conn = null;
        PreparedStatement addBookStmt = null;

        String addBookSQL = "INSERT INTO book VALUES (?, ?, ?, ?, ?, ?, ?, 0)";

        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Start a transaction
            conn.setAutoCommit(false);

            addBookStmt = conn.prepareStatement(addBookSQL);
            addBookStmt.setString(1, isbn);
            addBookStmt.setString(2, title);
            addBookStmt.setString(3, author);
            addBookStmt.setString(4, synopsis);
            addBookStmt.setString(5, genre);
            if (numberCopies != 0) {
            	addBookStmt.setString(6, "TRUE");
            } else addBookStmt.setString(6, "FALSE");
            addBookStmt.setInt(7, numberCopies);
            addBookStmt.executeUpdate();

            // Commit the transaction
            conn.commit();

            addBookStmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
        }
        // Optionally, refresh the list of users and forward back to the manage users page
        request.getRequestDispatcher("homepage.jsp").forward(request, response);
    }

}
