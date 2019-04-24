package com.hrithik.android.bongaf;

public class Product {

    private String image;
    private int type;
    private String video;
    private int views;
    private String title;
    public Product(){

    }

    public Product(String image, int type, String video,String title,int views) {
        this.image = image;
        this.type = type;
        this.video = video;
        this.views = views;
        this.title =  title;
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

    public String getTitle() {
        return title;
    }

    public int getViews(){
        return views;
    }
}