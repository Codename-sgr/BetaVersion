package com.sagar.betaversion.AdCategory;

public class SportsAd
{
    String id;
    String user_id;
    String brand;//item
    String model;
    String doP;
    String description;
    int sellingPrice;
    int img_count;
    boolean status;//true for unsold, false for sold
    public SportsAd()
    {

    }

    public SportsAd(String id, String user_id, String brand,String model,String doP, String description, int sellingPrice) {
        this.id = id;
        this.user_id = user_id;
        this.brand = brand;
        this.model=model;
        this.doP=doP;
        this.description = description;
        this.sellingPrice = sellingPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDoP() {
        return doP;
    }

    public void setDoP(String doP) {
        this.doP = doP;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getImg_count() {
        return img_count;
    }

    public void setImg_count(int img_count) {
        this.img_count = img_count;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
