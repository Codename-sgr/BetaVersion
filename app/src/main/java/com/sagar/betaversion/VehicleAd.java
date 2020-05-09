package com.sagar.betaversion;

import java.util.Date;

public class VehicleAd {
    String id;
    String user_id;
    String model;
    String date_of_purchase;
    String insurance;
    String milege;
    String sellingPrice;
    int img_count;
    String description;
    String fileExtension;

    boolean status;//true for unsold, false for sold
    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
    public VehicleAd()
     {

     }

    public VehicleAd(String id, String user_id, String model, String date_of_purchase, String insurance, String milege,String sellingPrice, String description) {
        this.id = id;
        this.user_id = user_id;
        this.model = model;
        this.date_of_purchase = date_of_purchase;
        this.insurance = insurance;
        this.milege = milege;
        this.sellingPrice=sellingPrice;
        this.description = description;
        this.img_count=0;
        this.status=true;
    }



    public String getId() {
        return id;
    }

    public int getImg_count() {
        return img_count;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getModel() {
        return model;
    }

    public void setImg_count(int img_count) {
        this.img_count = img_count;
    }

    public String getDate_of_purchase() {
        return date_of_purchase;
    }

    public String getInsurance() {
        return insurance;
    }

    public String getMilege() {
        return milege;
    }

    public String getSellingPrice(){
        return sellingPrice;
    }


    public String getDescription() {
        return description;
    }

    public boolean isStatus() {
        return status;
    }


}
