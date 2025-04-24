package ru.almidev.bookstore.models;

/**
 * Класс представляет сущность записи корзины пользователя приложения.
 */
public class UserCart {

    private Integer itemId;
    private AppUser user;
    private BookCatalog book;
    private Integer bookQuantity;

    public UserCart(Integer itemId, AppUser user, BookCatalog book, Integer bookQuantity) {
        this.itemId = itemId;
        this.user = user;
        this.book = book;
        this.bookQuantity = bookQuantity;
    }

    public UserCart() {

    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
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

    public Integer getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(Integer bookQuantity) {
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