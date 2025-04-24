package ru.almidev.bookstore.models;

/**
 * Класс представляет сущность автора книги.
 */
public class BookAuthor {
    private Integer authorId;
    private String lastName;
    private String firstName;
    private String middleName;
    private java.sql.Date birthDate;

    public BookAuthor() {
    }

    public BookAuthor(Integer authorId, String lastName, String firstName, String middleName, java.sql.Date birthDate) {
        this.authorId = authorId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public java.sql.Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(java.sql.Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "BookAuthor{" +
                "authorId=" + authorId +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}