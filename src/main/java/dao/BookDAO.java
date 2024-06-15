package dao;

import model.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookDAO{
    private static final String JDBC_URL = "jdbc:h2:tcp://localhost/C:\\Users\\migue\\Desktop\\Faculdade\\Semestre 6\\SCDist\\h2-2023-09-17\\scdistdb";
    private static final String JDBC_USER = "scdist";
    private static final String JDBC_PASSWORD = "scdist";
    

    public void addBook(Book book) throws Exception {
        Connection conn = null;
        PreparedStatement addBookStmt = null;

        String addBookSQL = "INSERT INTO book VALUES (?, ?, ?, ?, ?, ?, ?, 0)";

        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Start a transaction
            conn.setAutoCommit(false);

            addBookStmt = conn.prepareStatement(addBookSQL);
            addBookStmt.setString(1, book.getIsbn());
            addBookStmt.setString(2, book.getTitle());
            addBookStmt.setString(3, book.getAuthor());
            addBookStmt.setString(4, book.getSynopsis());
            addBookStmt.setString(5, book.getGenre());
            addBookStmt.setBoolean(6, book.isAvailable());
            addBookStmt.setInt(7, book.getNumberOfCopies());
            addBookStmt.executeUpdate();

            // Commit the transaction
            conn.commit();
        } catch (SQLException e) {
        	conn.rollback();
        } finally {
        conn.setAutoCommit(true);
        }	
    }
    public List<Book> getAllBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM book";

        try (
        		
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query)
        ) {
            Class.forName("org.h2.Driver");

            while (rs.next()) {
                Book book = new Book();
                book.setIsbn(rs.getString("isbn"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setSynopsis(rs.getString("abstract"));
                book.setGenre(rs.getString("genre"));
                book.setAvailable(rs.getBoolean("available"));
                book.setNumberOfCopies(rs.getInt("number_copies"));
                book.setNumberOfBorrowed(rs.getInt("number_borrowed"));

                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return books;
    }

    public void deleteBook(String isbn) throws Exception {
        String deleteRequestSQL = "DELETE FROM REQUEST WHERE isbn = ?";
        String deleteBookSQL = "DELETE FROM BOOK WHERE isbn = ?";

        try (
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement deleteRequestStmt = conn.prepareStatement(deleteRequestSQL);
            PreparedStatement deleteBookStmt = conn.prepareStatement(deleteBookSQL)
        ) {
            Class.forName("org.h2.Driver");

            // Start a transaction
            conn.setAutoCommit(false);

            // Delete from REQUEST table
            deleteRequestStmt.setString(1, isbn);
            deleteRequestStmt.executeUpdate();

            // Delete from BOOK table
            deleteBookStmt.setString(1, isbn);
            deleteBookStmt.executeUpdate();

            // Commit the transaction
            conn.commit();

            // Set auto-commit back to true
            conn.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    public List<Book> searchBooksByTitle(String title) throws Exception {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM BOOK WHERE LOWER(title) LIKE ?";

        Class.forName("org.h2.Driver");
        
        try (
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            Class.forName("org.h2.Driver");

            stmt.setString(1, "%" + title.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setIsbn(rs.getString("isbn"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setSynopsis(rs.getString("abstract"));
                book.setGenre(rs.getString("genre"));
                book.setAvailable(rs.getBoolean("available"));
                book.setNumberOfCopies(rs.getInt("number_copies"));
                book.setNumberOfBorrowed(rs.getInt("number_borrowed"));

                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return books;
    }
    public Book requestBook(String username, String isbn) throws SQLException {
        Book book = null;
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
        	
            conn.setAutoCommit(false);

            try (PreparedStatement updateBorrowedStmt = conn.prepareStatement("UPDATE book SET number_borrowed = number_borrowed + 1 WHERE isbn = ?");
                 PreparedStatement updateAvailableStmt = conn.prepareStatement("UPDATE book SET available = CASE WHEN number_copies = number_borrowed THEN FALSE ELSE available END WHERE isbn = ?");
                 PreparedStatement insertRequestStmt = conn.prepareStatement("INSERT INTO request (date_of_request, date_of_delivery, username, isbn) VALUES (?, ?, ?, ?)");
                 PreparedStatement getBookStmt = conn.prepareStatement("SELECT title, author FROM book WHERE isbn = ?")) {

                // Update the number of borrowed books
                updateBorrowedStmt.setString(1, isbn);
                updateBorrowedStmt.executeUpdate();

                // Update the availability status
                updateAvailableStmt.setString(1, isbn);
                updateAvailableStmt.executeUpdate();

                // Insert the request
                LocalDate currentDate = LocalDate.now();
                LocalDate deliveryDate = currentDate.plusMonths(2);
                insertRequestStmt.setDate(1, Date.valueOf(currentDate));
                insertRequestStmt.setDate(2, Date.valueOf(deliveryDate));
                insertRequestStmt.setString(3, username);
                insertRequestStmt.setString(4, isbn);
                insertRequestStmt.executeUpdate();

                // Retrieve book information
                getBookStmt.setString(1, isbn);
                ResultSet rs = getBookStmt.executeQuery();
                if (rs.next()) {
                    book = new Book();
                    book.setIsbn(isbn);
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
        return book;
    }
    public List<BookRequest> getUserRequests(String username) throws SQLException {
        List<BookRequest> requests = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            conn.setAutoCommit(false);
            try (PreparedStatement requestStmt = conn.prepareStatement("SELECT * FROM REQUEST WHERE username = ?");
                 PreparedStatement bookStmt = conn.prepareStatement("SELECT title FROM book WHERE isbn = ?")) {

                requestStmt.setString(1, username);
                ResultSet rs = requestStmt.executeQuery();

                while (rs.next()) {
                    String requestDate = rs.getString("date_of_request");
                    String deliveryDate = rs.getString("date_of_delivery");
                    String isbn = rs.getString("isbn");

                    bookStmt.setString(1, isbn);
                    ResultSet bookRs = bookStmt.executeQuery();

                    String bookTitle = null;
                    if (bookRs.next()) {
                        bookTitle = bookRs.getString("title");
                    }

                    requests.add(new BookRequest(requestDate, deliveryDate, username, isbn, bookTitle));
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
        return requests;
    }

    public void returnBook(String isbn) throws SQLException {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement deleteRequestStmt = conn.prepareStatement("DELETE FROM request WHERE isbn = ?");
                 PreparedStatement updateBorrowedStmt = conn.prepareStatement("UPDATE book SET number_borrowed = number_borrowed - 1 WHERE isbn = ?");
                 PreparedStatement updateAvailableStmt = conn.prepareStatement("UPDATE book SET available = CASE WHEN number_copies != number_borrowed THEN TRUE ELSE available END WHERE isbn = ?")) {

                deleteRequestStmt.setString(1, isbn);
                deleteRequestStmt.executeUpdate();

                updateBorrowedStmt.setString(1, isbn);
                updateBorrowedStmt.executeUpdate();

                updateAvailableStmt.setString(1, isbn);
                updateAvailableStmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
    
}
