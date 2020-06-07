package com.sagar.betaversion;

public class listAdModel {

    private String prodModel;
    private String prodBrand;
    private int prodPrice;
    private String prodImg;
    private String adId;
    private Boolean status;

    public listAdModel(String prodModel, String prodBrand, int prodPrice, String prodImg,String adId,Boolean status) {
        this.prodModel = prodModel;
        this.prodBrand = prodBrand;
        this.prodPrice = prodPrice;
        this.prodImg = prodImg;
        this.adId=adId;
        this.status=status;
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

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }
}