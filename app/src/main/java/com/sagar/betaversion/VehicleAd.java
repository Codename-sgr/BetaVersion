package com.sagar.betaversion;

import java.util.Date;

public class VehicleAd {
    String id;
    String user_id;
    String brand;
    String model;
    String date_of_purchase;
    int kmsDriven;
    int milege;
    int sellingPrice;
    int img_count;
    String description;
    String img1,img2,img3;
    boolean status;//true for unsold, false for sold

    public VehicleAd()
     {

     }

    public VehicleAd(String id, String user_id, String brand, String model, String  date_of_purchase, int kmsDriven, int milege, int sellingPrice, String description) {
        this.id = id;
        this.user_id = user_id;
        this.brand=brand;
        this.model = model;
        this.date_of_purchase = date_of_purchase;
        this.kmsDriven = kmsDriven;
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

    public String getBrand() {
        return brand;
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

    public int getKmsDriven() {
        return kmsDriven;
    }


    public int getMilege() {
        return milege;
    }

    public int getSellingPrice(){
        return sellingPrice;
    }


    public String getDescription() {
        return description;
    }

    public boolean isStatus() {
        return status;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }
}
