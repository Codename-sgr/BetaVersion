package com.sagar.betaversion;

public class Address {
    String user_Id;
    String addressID;
    String area;
    String city;
    String state;
    String pincode;
    public Address()
    {

    }

    public Address(String user_Id,String addressID, String area, String city, String state, String pincode) {
        this.user_Id=user_Id;
        this.addressID = addressID;
        this.area = area;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public String getAddressID() {
        return addressID;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPincode() {
        return pincode;
    }
}
