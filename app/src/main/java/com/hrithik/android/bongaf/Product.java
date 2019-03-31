package com.hrithik.android.bongaf;

public class Product {

    private String image;
    private int type;
    private String video;

    public Product(){

    }

    public Product(String image, int type, String video) {
        this.image = image;
        this.type = type;
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public int getType() {
        return type;
    }

    public String getVideo() {
        return video;
    }

}