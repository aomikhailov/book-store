package ru.almidev.bookstore.models;

/**
 * Класс представляет сущность записи корзины пользователя приложения.
 */
public class UserCart {

    private int itemId;
    private AppUser user;
    private BookCatalog book;
    private int bookQuantity;

    public UserCart(int itemId, AppUser user, BookCatalog book, int bookQuantity) {
        this.itemId = itemId;
        this.user = user;
        this.book = book;
        this.bookQuantity = bookQuantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public BookCatalog getBook() {
        return book;
    }

    public void setBook(BookCatalog book) {
        this.book = book;
    }

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    @Override
    public String toString() {
        return "UserCart{" +
                "itemId=" + itemId +
                ", user=" + user +
                ", book=" + book +
                ", bookQuantity=" + bookQuantity +
                '}';
    }

}