package com.example.project_palm_on;

public class SliderItem {
    private String title;
    private String date;
    private int imageResourceId;

    public SliderItem(String title, String date, int imageResourceId) {
        this.title = title;
        this.date = date;
        this.imageResourceId = imageResourceId;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
