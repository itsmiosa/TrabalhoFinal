package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/TrabalhoFinal/AddUserServlet")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String JDBC_URL = "jdbc:h2:tcp://localhost/C:\\Users\\migue\\Desktop\\Faculdade\\Semestre 6\\SCDist\\h2-2023-09-17\\scdistdb";
	private static final String JDBC_USER = "scdist";
	private static final String JDBC_PASSWORD = "scdist";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String nif = request.getParameter("nif");
        String role = null;
        role = request.getParameter("role");
        
        try {
			password = PassHash.generateHash(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Connection conn = null;
        PreparedStatement addUserStmt = null;

        String addUserSQL = "INSERT INTO PERSON (NIF, USERNAME, PASSWORD, EMAIL) VALUES (?, ?, ?, ?)";
        

        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Start a transaction
            conn.setAutoCommit(false);

            addUserStmt = conn.prepareStatement(addUserSQL);
            addUserStmt.setString(1, nif);
            addUserStmt.setString(2, username);
            addUserStmt.setString(3, password);
            addUserStmt.setString(4, email);
            addUserStmt.executeUpdate();

            PreparedStatement updateUserRoleStmt = conn.prepareStatement("INSERT INTO user_role values (?, ?)");
            
            updateUserRoleStmt.setString(1, username);
            updateUserRoleStmt.setString(2, "user");
            updateUserRoleStmt.executeUpdate();
            
            role = role.toString();
            System.out.println(role);
                        
            // Commit the transaction
            conn.commit();

            addUserStmt.close();
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
        request.getRequestDispatcher("homepage.jsp").forward(request, response);
    }

}
 