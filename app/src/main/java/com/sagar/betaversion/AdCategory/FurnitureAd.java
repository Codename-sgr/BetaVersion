package com.sagar.betaversion.AdCategory;

public class FurnitureAd
{
    String id;
    String user_id;
    String model;
    String doP;
    String description;
    String sellingPrice;
    int img_count;
    boolean status;
    public FurnitureAd()
    {

    }

    public FurnitureAd(String id, String user_id, String model,String doP, String description, String sellingPrice) {
        this.id = id;
        this.user_id = user_id;
        this.model = model;
        this.doP=doP;
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

    public String getModel() {
        return model;
    }

    public void setModel(String item) {
        this.model = item;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

