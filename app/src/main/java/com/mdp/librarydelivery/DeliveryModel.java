package com.mdp.librarydelivery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DeliveryModel{
    private String id;
    private String deliverer_name;
    private String delivery_address;
    private Date delivery_date;
    private String email;
    private String loan_id;
    private String phone_no;
    private String receiver_id;
    private String receiver_name;
    private String status;
    private String type;

    public DeliveryModel(){}

    public DeliveryModel(String deliverer_name, String delivery_address, Date delivery_date, String email, String loan_id,String phone_no, String receiver_id, String receiver_name, String status, String type){
        this.deliverer_name =deliverer_name;
        this.delivery_address =delivery_address;
        this.delivery_date = delivery_date;
        this.email = email;
        this.loan_id = loan_id;
        this.receiver_id = receiver_id;
        this.receiver_name = receiver_name;
        this.status = status;
        this.phone_no = phone_no;
        this.type = type;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getDeliverer_name() {
        return deliverer_name;
    }

    public void setDeliverer_name(String deliverer_name){ this.deliverer_name = deliverer_name; }

    public String getDelivery_address(){return this.delivery_address;}

    public void setDelivery_address(String delivery_address){ this.delivery_address = delivery_address;}

    public String getDelivery_date(){
        String date_string = new SimpleDateFormat("yyyy-MM-dd").format(delivery_date);
        return date_string;
    }

    public void setDelivery_date(Date delivery_date){
        this.delivery_date = delivery_date;
    }

    public String getEmail(){return this.email;}

    public void setEmail(String email){this.email = email;}

    public String getLoan_id(){return this.loan_id;}

    public void setLoan_id(String loan_id){this.loan_id = loan_id;}

    public String getType(){return this.type;}

    public void setType(String type){this.type = type;}

    public String getReceiver_id(){return this.receiver_id;}

    public void setReceiver_id(String receiver_id){this.receiver_id =receiver_id;}

    public String getReceiver_name(){return this.receiver_name;}

    public void setReceiver_name(String receiver_name){this.receiver_name =receiver_name;}

    public String getStatus(){return this.status;}

    public void setStatus(String status){this.status =status;}

    public String getPhone_no(){return this.phone_no;}

    public void setPhone_no(String phone_no){this.phone_no =phone_no;
    }
}