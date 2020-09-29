package com.manitkart.app.models;

public class BooksAd {
    String id;
    String user_id;
    String brand;// Author
    String model;// Title
    String date_of_purchase;
    int vs;
    String description;
    int sellingPrice;
    String img1,img2,img3;
    int img_count;
    boolean status;//true for unsold, false for sold
    public BooksAd()
    {

    }
    public BooksAd(String id, String user_id, String brand,String model,String date_of_purchase, String description, int sellingPrice) {
        this.id = id;
        this.user_id = user_id;
        this.brand=brand;
        this.model=model;
        this.date_of_purchase=date_of_purchase;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.status=true;
        this.vs=0;
    }

    public String getId() {
        return id;
    }

    public int getVs() {
        return vs;
    }

    public void setVs(int vs) {
        this.vs = vs;
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

    public String getDate_of_purchase() {
        return date_of_purchase;
    }

    public void setDate_of_purchase(String date_of_purchase) {
        this.date_of_purchase = date_of_purchase;
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
