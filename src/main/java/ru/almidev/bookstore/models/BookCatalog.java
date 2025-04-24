package ru.almidev.bookstore.models;

/**
 * Класс представляет сущность записи каталога книг.
 */
public class BookCatalog {

    private Integer bookId;
    private String title;
    private BookAuthor author;
    private Double price;

    public BookCatalog(Integer bookId, String title, BookAuthor author, Double price) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public BookCatalog() {

    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookAuthor getAuthor() {
        return author;
    }

    public void setAuthor(BookAuthor author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "BookCatalog{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", price=" + price +
                '}';
    }
}