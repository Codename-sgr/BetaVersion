package com.sagar.betaversion.AdCategory;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ElectronicsAd {
    String id;
    String user_id;
    String brand;
    String model;
    String date_of_purchase,img1,img2,img3;
    String description;
    int img_count;
    int sellingPrice;

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

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getDate_of_purchase() {
        return date_of_purchase;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStatus() {
        return status;
    }

    public int getImg_count() {
        return img_count;
    }

    public int getSellingPrice(){
        return sellingPrice;
    }



    public ElectronicsAd(String id, String user_id,String brand, String model, String date_of_purchase, String description, int sellingPrice) {
        this.id = id;
        this.user_id = user_id;
        this.brand=brand;
        this.model = model;
        this.date_of_purchase = date_of_purchase;
        this.description = description;
        this.sellingPrice=sellingPrice;

        this.status=true;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setDate_of_purchase(String date_of_purchase) {
        this.date_of_purchase = date_of_purchase;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImg_count(int img_count) {
        this.img_count = img_count;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
