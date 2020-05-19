package com.sagar.betaversion;

public class User {
    String user_id;
    String user_name;
    String email;
    String mobile;
    boolean dp;

    String address;
    public User()
    {

    }

    public User(String user_id, String user_name, String email) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.email = email;

    }


    public User(String user_id, String user_name, String email, String mobile, String address) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.dp=false;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setDp(boolean dp) {
        this.dp = dp;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public boolean isDp() {
        return dp;
    }

    public String getAddress() {
        return address;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getEmail() {
        return email;
    }


}
