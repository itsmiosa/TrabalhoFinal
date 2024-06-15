package dao;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class UserDAO {
	private static final String JDBC_URL = "jdbc:h2:tcp://localhost/C:\\Users\\migue\\Desktop\\Faculdade\\Semestre 6\\SCDist\\h2-2023-09-17\\scdistdb";
	private static final String JDBC_USER = "scdist";
	private static final String JDBC_PASSWORD = "scdist";

	public UserDAO() {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void addUserAndRole(User user, Role role) throws SQLException {
		String addUserSQL = "INSERT INTO PERSON (NIF, USERNAME, PASSWORD, EMAIL) VALUES (?, ?, ?, ?)";
		String addUserRoleSQL = "INSERT INTO user_role (username, role) VALUES (?, ?)";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
				PreparedStatement addUserStmt = conn.prepareStatement(addUserSQL);
				PreparedStatement addUserRoleStmt = conn.prepareStatement(addUserRoleSQL)) {

			conn.setAutoCommit(false);

			addUserStmt.setString(1, user.getNif());
			addUserStmt.setString(2, user.getUsername());
			addUserStmt.setString(3, user.getPassword());
			addUserStmt.setString(4, user.getEmail());
			addUserStmt.executeUpdate();

			addUserRoleStmt.setString(1, user.getUsername());
			addUserRoleStmt.setString(2, role.getRole());
			addUserRoleStmt.executeUpdate();

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	public void addUserAndRoles(User user, List<Role> roles) throws SQLException {
		String addUserSQL = "INSERT INTO PERSON (NIF, USERNAME, PASSWORD, EMAIL) VALUES (?, ?, ?, ?)";
		String addUserRoleSQL = "INSERT INTO user_role (username, name) VALUES (?, ?)";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
				PreparedStatement addUserStmt = conn.prepareStatement(addUserSQL);
				PreparedStatement addUserRoleStmt = conn.prepareStatement(addUserRoleSQL)) {

			conn.setAutoCommit(false);

			addUserStmt.setString(1, user.getNif());
			addUserStmt.setString(2, user.getUsername());
			addUserStmt.setString(3, user.getPassword());
			addUserStmt.setString(4, user.getEmail());
			addUserStmt.executeUpdate();

			for (Role role : roles) {
				addUserRoleStmt.setString(1, user.getUsername());
				addUserRoleStmt.setString(2, role.getRole());
				addUserRoleStmt.executeUpdate();
			}

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	public List<User> getAllUsers() throws SQLException {
		List<User> users = new ArrayList<>();
		String query = "SELECT * FROM PERSON";

		try (
				Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
				Statement statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(query)) {
			while (rs.next()) {
				String nif = rs.getString("nif");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String email = rs.getString("email");
				
				User user = new User(username, password, email, nif);
				users.add(user);
			}
		}
		return users;
	}

	public void deleteUser(String username) throws SQLException {
		String deleteRequestSQL = "DELETE FROM REQUEST WHERE username = ?";
		String deleteUserRoleSQL = "DELETE FROM USER_ROLE WHERE username = ?";
		String deletePersonSQL = "DELETE FROM PERSON WHERE username = ?";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
			conn.setAutoCommit(false);

			// Delete from REQUEST table
			try (PreparedStatement deleteRequestStmt = conn.prepareStatement(deleteRequestSQL);
					PreparedStatement deleteUserRoleStmt = conn.prepareStatement(deleteUserRoleSQL);
					PreparedStatement deletePersonStmt = conn.prepareStatement(deletePersonSQL)
					) {
				deleteRequestStmt.setString(1, username);
				deleteRequestStmt.executeUpdate();

				deleteUserRoleStmt.setString(1, username);
				deleteUserRoleStmt.executeUpdate();

				deletePersonStmt.setString(1, username);
				deletePersonStmt.executeUpdate();

				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		}
	}

}
