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

    public String getDescription() {
        return description;
    }

    public boolean isStatus() {
        return status;
    }
}
