package com.sagar.betaversion;

public class FurnitureAd
{
    String id;
    String user_id;
    String item;
    String year_of_purchase;
    String description;
    String sellingPrice;
    int img_count;
    String fileExtension;
    boolean status;
    public FurnitureAd()
    {

    }

    public FurnitureAd(String id, String user_id, String item, String year_of_purchase, String description, String sellingPrice) {
        this.id = id;
        this.user_id = user_id;
        this.item = item;
        this.year_of_purchase = year_of_purchase;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.status=true;
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

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getYear_of_purchase() {
        return year_of_purchase;
    }

    public void setYear_of_purchase(String year_of_purchase) {
        this.year_of_purchase = year_of_purchase;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getImg_count() {
        return img_count;
    }

    public void setImg_count(int img_count) {
        this.img_count = img_count;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

