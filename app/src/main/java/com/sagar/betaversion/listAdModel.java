package com.sagar.betaversion;

import android.net.Uri;

public class listAdModel {

    private String prodModel;
    private String prodBrand;
    private int prodPrice;
    private String prodImg;

    public listAdModel(String prodModel, String prodBrand, int prodPrice, String prodImg) {
        this.prodModel = prodModel;
        this.prodBrand = prodBrand;
        this.prodPrice = prodPrice;
        this.prodImg = prodImg;
    }

    public String getProdModel() {
        return prodModel;
    }

    public void setProdModel(String prodModel) {
        this.prodModel = prodModel;
    }

    public String getProdBrand() {
        return prodBrand;
    }

    public void setProdBrand(String prodBrand) {
        this.prodBrand = prodBrand;
    }

    public int getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(int prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getProdImg() {
        return prodImg;
    }

    public void setProdImg(String prodImg) {
        this.prodImg = prodImg;
    }
}