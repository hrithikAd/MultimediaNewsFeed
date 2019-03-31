package com.hrithik.android.bongaf;

public class Product {

    private String image;
    private String title;
    private String video;
    private int views;

    public Product(){

    }

    public Product(String image, String title, double rating, String video) {
        this.image = image;
        this.title = title;
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getVideo() {
        return video;
    }

}