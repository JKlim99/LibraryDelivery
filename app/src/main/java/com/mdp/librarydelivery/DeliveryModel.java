package com.mdp.librarydelivery;

public class DeliveryModel{
    private String id;
    private String deliverer_name;
    private String delivery_address;
    private String delivery_date;
    private String email;
    private int loan_id;
    private String phone_no;
    private String receiver_id;
    private String receiver_name;
    private String status;

    public DeliveryModel(){}

    public DeliveryModel(String deliverer_name, String delivery_address, String delivery_date, String email, int loan_id,String phone_no, String receiver_id, String receiver_name, String status){
        this.deliverer_name =deliverer_name;
        this.delivery_address =delivery_address;
        this.delivery_date = delivery_date;
        this.email = email;
        this.loan_id = loan_id;
        this.receiver_id =receiver_id;
        this.receiver_name =receiver_name;
        this.status =status;
        this.phone_no =phone_no;
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

    public String getDelivery_date(){return this.delivery_date;}

    public void setDelivery_date(String delivery_date){this.delivery_date = delivery_date;}

    public String getEmail(){return this.email;}

    public void setEmail(String email){this.email = email;}

    public int getLoan_id(){return this.loan_id;}

    public void setLoan_id(int loan_id){this.loan_id = loan_id;}

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