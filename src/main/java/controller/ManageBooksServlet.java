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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/TrabalhoFinal/ManageBooksServlet")
public class ManageBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String JDBC_URL = "jdbc:h2:tcp://localhost/C:\\Users\\migue\\Desktop\\Faculdade\\Semestre 6\\SCDist\\h2-2023-09-17\\scdistdb";
	private static final String JDBC_USER = "scdist";
	private static final String JDBC_PASSWORD = "scdist";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		List<Object[]> books = new ArrayList<>();
		Connection conn = null;
		Statement statement = null;
		String query = null;

		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
			
			statement = conn.createStatement();
			query = "SELECT * FROM book";
			
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				String isbn = rs.getString("isbn");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String abstractText = rs.getString("abstract");
				String genre = rs.getString("genre");
				boolean available = rs.getBoolean("available");
				int numberCopies = rs.getInt("number_copies");
				int numberBorrowed = rs.getInt("number_borrowed");
				
				

				Object[] bookData = {isbn, title, author, abstractText, genre, available, numberCopies, numberBorrowed};
				books.add(bookData);
			}
			rs.close();
			statement.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("books", books);
		RequestDispatcher dispatcher = request.getRequestDispatcher("admin/manageBooks.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbn = request.getParameter("isbn");

        Connection conn = null;
        PreparedStatement deleteRequestStmt = null;
        PreparedStatement deleteBookStmt = null;

        String deleteRequestSQL = "DELETE FROM REQUEST WHERE isbn = ?";
        String deleteBookSQL = "DELETE FROM BOOK WHERE isbn = ?";

        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Start a transaction
            conn.setAutoCommit(false);

            // Delete from REQUEST table
            deleteRequestStmt = conn.prepareStatement(deleteRequestSQL);
            deleteRequestStmt.setString(1, isbn);
            deleteRequestStmt.executeUpdate();

            // Delete from BOOK table
            deleteBookStmt = conn.prepareStatement(deleteBookSQL);
            deleteBookStmt.setString(1, isbn);
            deleteBookStmt.executeUpdate();
            
            // Commit the transaction
            conn.commit();

            deleteRequestStmt.close();
            deleteBookStmt.close();
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
        doGet(request, response);
    }
}
