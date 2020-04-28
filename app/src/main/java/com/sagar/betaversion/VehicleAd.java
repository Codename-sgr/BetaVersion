package com.sagar.betaversion;

import java.util.Date;

public class VehicleAd {
    String id;
    String user_id;
    String model;
    String date_of_purchase;
    String insurance;
    int milege;
    String description;
    String url1;
    String url2;
    String url3;
    boolean status;//true for unsold, false for sold

    public VehicleAd()
     {

     }

    public VehicleAd(String id, String user_id, String model, String date_of_purchase, String insurance, int milege, String description) {
        this.id = id;
        this.user_id = user_id;
        this.model = model;
        this.date_of_purchase = date_of_purchase;
        this.insurance = insurance;
        this.milege = milege;
        this.description = description;
        this.url1 = null;
        this.url2 = null;
        this.url3 = null;
        this.status=true;
    }



    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getModel() {
        return model;
    }

    public String getDate_of_purchase() {
        return date_of_purchase;
    }

    public String getInsurance() {
        return insurance;
    }

    public int getMilege() {
        return milege;
    }

    public String getUrl1() {
        return url1;
    }

    public String getUrl2() {
        return url2;
    }

    public String getUrl3() {
        return url3;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }
}
