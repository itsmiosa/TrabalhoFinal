package model;

public class Book {
	private String isbn;
    private String title;
    private String author;
    private String synopsis;
    private String genre;
    private boolean available;
    private int numberOfCopies;
    private int numberOfBorrowed;
    
    
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public int getNumberOfCopies() {
		return numberOfCopies;
	}
	public void setNumberOfCopies(int numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}
	public Book(String isbn, String title, String author, String synopsis, String genre, boolean available,
			int numberOfCopies) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.synopsis = synopsis;
		this.genre = genre;
		this.available = available;
		this.numberOfCopies = numberOfCopies;
		this.numberOfBorrowed = numberOfBorrowed;
	}
	public Book() {
		
	}
	public int getNumberOfBorrowed() {
		return numberOfBorrowed;
	}
	public void setNumberOfBorrowed(int numberOfBorrowed) {
		this.numberOfBorrowed = numberOfBorrowed;
	}
    
    
   
}
