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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/SearchBookServlet")
public class SearchBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String JDBC_URL = "jdbc:h2:tcp://localhost/C:\\Users\\migue\\Desktop\\Faculdade\\Semestre 6\\SCDist\\h2-2023-09-17\\scdistdb";
	private static final String JDBC_USER = "scdist";
	private static final String JDBC_PASSWORD = "scdist";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bookQuery = request.getParameter("bookQuery");
		response.setContentType("text/html;charset=UTF-8");

		List<Object[]> books = new ArrayList<>();
		Connection conn = null;
		Statement statement = null;
		String query = null;

		try {
			conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
			statement = conn.createStatement();
			query = "select * from BOOK where LOWER(title) like '%" + bookQuery + "%'";

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
		RequestDispatcher dispatcher = request.getRequestDispatcher("showBook.jsp");
		dispatcher.forward(request, response);
	}
}
