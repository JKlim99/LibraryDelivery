package com.mdp.librarydelivery;


public class UserModel {
    private String id;
    private String username;
    private String user_type;
    private String phone_number;
    private String email;
    private String password;
    private String address;

    public UserModel(){}

    public UserModel(String username, String user_type, String phone_number, String email, String password, String address){
        this.username =username;
        this.user_type =user_type;
        this.phone_number = phone_number;
        this.email = email;
        this.password = password;
        this.address =address;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){ this.username = username; }

    public String getUser_type(){return this.user_type;}

    public void setUser_type(String user_type){ this.user_type = user_type;}

    public String getPhone_number(){return this.phone_number;}

    public void setPhone_number(String phone_number){this.phone_number = phone_number;}

    public String getEmail(){return this.email;}

    public void setEmail(String email){this.email = email;}

    public String getPassword(){return this.password;}

    public void setPassword(String password){this.password = password;}

    public String getAddress(){return this.address;}

    public void setAddress(String address){this.address =address;}


}