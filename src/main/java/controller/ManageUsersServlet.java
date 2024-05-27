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

@WebServlet("/TrabalhoFinal/ManageUsersServlet")
public class ManageUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String JDBC_URL = "jdbc:h2:tcp://localhost/C:\\Users\\migue\\Desktop\\Faculdade\\Semestre 6\\SCDist\\h2-2023-09-17\\scdistdb";
	private static final String JDBC_USER = "scdist";
	private static final String JDBC_PASSWORD = "scdist";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		List<Object[]> users = new ArrayList<>();
		Connection conn = null;
		Statement statement = null;
		String query = null;

		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
			statement = conn.createStatement();
			query = "select * from PERSON";

			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				String nif = rs.getString("nif");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String email = rs.getString("email");

				Object[] userData = {nif, username, password, email};
				users.add(userData);
			}
			rs.close();
			statement.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("users", users);
		RequestDispatcher dispatcher = request.getRequestDispatcher("admin/manageUsers.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nif = request.getParameter("nif");

        Connection conn = null;
        PreparedStatement deleteRequestStmt = null;
        PreparedStatement deleteUserRoleStmt = null;
        PreparedStatement deletePersonStmt = null;

        String deleteRequestSQL = "DELETE FROM REQUEST WHERE nif = ?";
        String deleteUserRoleSQL = "DELETE FROM USER_ROLE WHERE nif = ?";
        String deletePersonSQL = "DELETE FROM PERSON WHERE nif = ?";

        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Start a transaction
            conn.setAutoCommit(false);

            // Delete from REQUEST table
            deleteRequestStmt = conn.prepareStatement(deleteRequestSQL);
            deleteRequestStmt.setString(1, nif);
            deleteRequestStmt.executeUpdate();

            // Delete from USER_ROLE table
            deleteUserRoleStmt = conn.prepareStatement(deleteUserRoleSQL);
            deleteUserRoleStmt.setString(1, nif);
            deleteUserRoleStmt.executeUpdate();

            // Delete from PERSON table
            deletePersonStmt = conn.prepareStatement(deletePersonSQL);
            deletePersonStmt.setString(1, nif);
            deletePersonStmt.executeUpdate();

            // Commit the transaction
            conn.commit();

            deleteRequestStmt.close();
            deleteUserRoleStmt.close();
            deletePersonStmt.close();
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
