package com.sagar.betaversion;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ElectronicsAd {
    String id;
    String user_id;
    String model;
    String date_of_purchase;
    String insurance;
    String description;
    int img_count;
    String sellingPrice;
    String fileExtension;
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

    public String getDate_of_purchase() {
        return date_of_purchase;
    }

    public String getInsurance() {
        return insurance;
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

    public String getSellingPrice(){
        return sellingPrice;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public ElectronicsAd(String id, String user_id, String model, String date_of_purchase, String insurance, String description, String sellingPrice) {
        this.id = id;
        this.user_id = user_id;
        this.model = model;
        this.date_of_purchase = date_of_purchase;
        this.insurance = insurance;
        this.description = description;
        this.sellingPrice=sellingPrice;

        this.status=true;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setDate_of_purchase(String date_of_purchase) {
        this.date_of_purchase = date_of_purchase;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImg_count(int img_count) {
        this.img_count = img_count;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
