package com.mdp.librarydelivery;

public class BookModel {
    private String id;
    private String book_name;
    private String image;
    private String ISBN;
    private String author;
    private String category;
    private String description;
    private String status;

    public BookModel() {

    }

    public BookModel(String book_name, String image, String ISBN, String author, String category, String description, String status) {
        this.book_name = book_name;
        this.image = image;
        this.ISBN = ISBN;
        this.author = author;
        this.category = category;
        this.description = description;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBookName(String book_name) {
        this.book_name = book_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsbn() {
        return ISBN;
    }

    public void setIsbn(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
