package com.sagar.betaversion;

import java.util.Date;

public class OtherAd {
    String id;
    String user_id;
    String item;
    int year_of_purchase;
    String description;
    boolean status;//true for unsold, false for sold

    public OtherAd()
    {

    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getItem() {
        return item;
    }

    public int getYear_of_purchase() {
        return year_of_purchase;
    }

    public boolean isStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public OtherAd(String id, String user_id, String item, int year_of_purchase, String description) {
        this.id = id;
        this.user_id = user_id;
        this.item = item;
        this.year_of_purchase = year_of_purchase;
        this.description = description;
        this.status=true;
    }
}
