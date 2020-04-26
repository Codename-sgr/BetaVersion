package com.sagar.betaversion;

public class Image {

    String image_id;
    String imageUrl;
    public Image()
    {

    }

    public Image(String image_id, String imageUrl) {

        this.image_id = image_id;
        this.imageUrl = imageUrl;
    }

    public String getImage_id() {
        return image_id;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
