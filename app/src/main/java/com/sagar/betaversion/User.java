package com.sagar.betaversion;

public class User {
    String user_id;
    String user_name;
    String email;
    String password;
    int contact;
    public User()
    {

    }

    public User(String user_id, String user_name, String email, String password, int contact) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.contact=contact;
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

    public String getPassword() {
        return password;
    }

    public int getContact() {
        return contact;
    }
}
