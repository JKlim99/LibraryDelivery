package com.mdp.librarydelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BookLoanModel {
    private String loan_id;
    private String book_id;
    private String request_date;
    private String return_date;
    private String status;
    private String user_id;

    public BookLoanModel() {

    }

    public BookLoanModel(String book_id, String request_date, String return_date, String status, String user_id, String loan_id) {
        this.loan_id = loan_id;
        this.book_id = book_id;
        this.request_date = request_date;
        this.return_date = return_date;
        this.status = status;
        this.user_id = user_id;
    }

    public String getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(String loan_id) {
        this.loan_id = loan_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public String getRequest_date() {
        return request_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public String getStatus() {
        return status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}