package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ReturnBookServlet")
public class ReturnBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String JDBC_URL = "jdbc:h2:tcp://localhost/C:\\Users\\migue\\Desktop\\Faculdade\\Semestre 6\\SCDist\\h2-2023-09-17\\scdistdb";
    private static final String JDBC_USER = "scdist";
    private static final String JDBC_PASSWORD = "scdist";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Principal userPrincipal = request.getUserPrincipal();
        String user = userPrincipal.getName();

        List<Object[]> requests = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;
        Statement statement2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;

        try {
        	Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            conn.setAutoCommit(false); // Start transaction

            statement = conn.createStatement();
            statement2 = conn.createStatement();
            String query = "select * from REQUEST where username = '" + user + "'";

            rs = statement.executeQuery(query);

            while (rs.next()) {
                String requestDate = rs.getString("date_of_request");
                String deliveryDate = rs.getString("date_of_delivery");
                String username = rs.getString("username");
                String isbn = rs.getString("isbn");
                String bookTitle = null;

                String bookQuery = "select title from book where isbn like '" + isbn + "'";
                rs2 = statement2.executeQuery(bookQuery);

                if (rs2.next()) {
                    bookTitle = rs2.getString("title");
                }

                Object[] requestData = {requestDate, deliveryDate, username, isbn, bookTitle};
                requests.add(requestData);
            }

            conn.commit(); // Commit transaction
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback(); // Rollback transaction on error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (rs2 != null) rs2.close();
                if (rs != null) rs.close();
                if (statement2 != null) statement2.close();
                if (statement != null) statement.close();
                if (conn != null) {
                    conn.setAutoCommit(true); // Restore auto-commit mode
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("requests", requests);

        RequestDispatcher dispatcher;
        dispatcher = request.getRequestDispatcher("/user/returnBook.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Connection conn = null;
        Statement statement = null;
        String isbn = request.getParameter("isbn");

        try {
        	Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            conn.setAutoCommit(false); // Start transaction

            statement = conn.createStatement();
            
            String query = "delete from request where isbn = '" + isbn + "'";
            statement.executeUpdate(query);
            
            query = "update book set number_borrowed = number_borrowed - 1 where isbn = '" + isbn + "'";
            statement.executeUpdate(query);
            
            query = "UPDATE book SET available = CASE WHEN number_copies != number_borrowed THEN TRUE ELSE available END WHERE isbn = '" + isbn + "'";
            statement.executeUpdate(query);
            
            conn.commit(); // Commit transaction
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback(); // Rollback transaction on error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) {
                    conn.setAutoCommit(true); // Restore auto-commit mode
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        doGet(request, response);
    }
}
