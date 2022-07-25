package com.mdp.librarydelivery;

public class BookModel {
    private String id;
    private String book_name;
    private String image;

    public BookModel() {

    }

    public BookModel(String book_name, String image) {
        this.book_name = book_name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getImage() {
        return image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBookName(String book_name) {
        this.book_name = book_name;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
