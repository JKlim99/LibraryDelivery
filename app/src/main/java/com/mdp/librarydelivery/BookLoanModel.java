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

    public BookLoanModel() {

    }

    public BookLoanModel(String user_id, Date request_date, Date return_date, String status, String book_id) {
        this.user_id = user_id;
        this.request_date = request_date;
        this.return_date = return_date;
        this.status = status;
        this.book_id = book_id;
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
