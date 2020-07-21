package com.sagar.betaversion;

public class listAdModel {

    private String prodModel;
    private String prodBrand;
    private int prodPrice;
    private String prodImg;
    private String adId;
    private  String uadId;
    private Boolean status;
    private  String type;
    private String owner_id;
    private int vs;

    public listAdModel(String prodModel, String prodBrand, int prodPrice, String prodImg,String adId,Boolean status,String type,int vs,String uadId, String owner_id) {
        this.prodModel = prodModel;
        this.prodBrand = prodBrand;
        this.prodPrice = prodPrice;
        this.prodImg = prodImg;
        this.adId=adId;
        this.status=status;
        this.vs=vs;
        this.type=type;
        this.uadId=uadId;
        this.owner_id=owner_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProdModel() {
        return prodModel;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public int getVs() {
        return vs;
    }

    public void setVs(int vs) {
        this.vs = vs;
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

    public String getUadId() {
        return uadId;
    }

    public void setUadId(String uadId) {
        this.uadId = uadId;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }
}