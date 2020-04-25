package com.sagar.betaversion;

import java.util.Date;

public class ElectronicsAd {
    String id;
    String user_id;
    String model;
    Date date_of_purchase;
    Date insurance;
    String description;
    boolean status;//true for unsold, false for sold
    public ElectronicsAd()
    {

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

    public Date getDate_of_purchase() {
        return date_of_purchase;
    }

    public Date getInsurance() {
        return insurance;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStatus() {
        return status;
    }

    public ElectronicsAd(String id, String user_id, String model, Date date_of_purchase, Date insurance, String description) {
        this.id = id;
        this.user_id = user_id;
        this.model = model;
        this.date_of_purchase = date_of_purchase;
        this.insurance = insurance;
        this.description = description;
        this.status=true;
    }
}
