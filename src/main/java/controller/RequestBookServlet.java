package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/RequestBookServlet")
public class RequestBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String JDBC_URL = "jdbc:h2:tcp://localhost/C:\\Users\\migue\\Desktop\\Faculdade\\Semestre 6\\SCDist\\h2-2023-09-17\\scdistdb";
	private static final String JDBC_USER = "scdist";
	private static final String JDBC_PASSWORD = "scdist";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");


		String isbn = request.getParameter("isbn");
		Connection conn = null;
		
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

			PreparedStatement updateBorrowedStmt = conn.prepareStatement("UPDATE book SET number_borrowed = number_borrowed + 1 WHERE isbn = ?");
            PreparedStatement updateAvailableStmt = conn.prepareStatement("UPDATE book SET available = CASE WHEN number_copies = number_borrowed THEN FALSE ELSE available END WHERE isbn = ?"); 
           

            // Update the number of borrowed books
            updateBorrowedStmt.setString(1, isbn);
            updateBorrowedStmt.executeUpdate();

            // Update the availability status
            updateAvailableStmt.setString(1, isbn);
            updateAvailableStmt.executeUpdate();
			
            PreparedStatement getBookNameStmt = conn.prepareStatement("SELECT title, author FROM book WHERE isbn = ?");
            getBookNameStmt.setString(1, isbn);
            ResultSet rs = getBookNameStmt.executeQuery();

            String title = null;
            String author = null;
            while (rs.next()) {
                title = rs.getString("title");
                author = rs.getString("author");
            }

            conn.close();

            // Set the book name as a request attribute
            request.setAttribute("title", title);
            request.setAttribute("author", author);
            } catch (Exception e) {
            	e.printStackTrace();
            }
		
		request.setAttribute("isbn", isbn);
		RequestDispatcher dispatcher = request.getRequestDispatcher("confirmation.jsp");
		dispatcher.forward(request, response);
	}
}
