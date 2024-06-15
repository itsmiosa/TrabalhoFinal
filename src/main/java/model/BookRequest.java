package model;

public class BookRequest {
    private String requestDate;
    private String deliveryDate;
    private String username;
    private String isbn;
    private String bookTitle;

    public BookRequest(String requestDate, String deliveryDate, String username, String isbn, String bookTitle) {
        this.requestDate = requestDate;
        this.deliveryDate = deliveryDate;
        this.username = username;
        this.isbn = isbn;
        this.bookTitle = bookTitle;
    }

    // Getters and Setters
    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}
