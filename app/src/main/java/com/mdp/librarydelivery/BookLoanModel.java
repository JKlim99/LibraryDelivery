package com.mdp.librarydelivery;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BookLoanModel {
    private String id;
    private String user_id;
    private Date request_date;
    private Date return_date;
    private String status;
    private String book_id;
    private String book_name;
    private String image;
    private String fine_amount;

    public BookLoanModel() {

    }

    public BookLoanModel(String user_id, Date request_date, Date return_date, String status, String book_id, String fine_amount) {
        this.user_id = user_id;
        this.request_date = request_date;
        this.return_date = return_date;
        this.status = status;
        this.book_id = book_id;
        this.fine_amount = fine_amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_Id() {
        return user_id;
    }

    public void setUser_Id( String user_id) {
        this.user_id = user_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name( String book_name) {
        this.book_name = book_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage( String image) {
        this.image = image;
    }

    public String getFine_amount() {
        return fine_amount;
    }

    public void setFine_amount( String fine_amount) {
        this.fine_amount = fine_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequest_date() {
        String request_date_string = new SimpleDateFormat("yyyy-MM-dd").format(request_date);
        return request_date_string;
    }

    public void setRequest_date(Date request_date) {
        this.request_date = request_date;
    }

    public String getReturn_date() {
        String return_date_string = new SimpleDateFormat("yyyy-MM-dd").format(return_date);
        return return_date_string;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }
}
