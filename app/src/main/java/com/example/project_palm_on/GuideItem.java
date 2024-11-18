package com.example.project_palm_on;


public class GuideItem {
    private String author;
    private String title;
    private String description;
    private String timeUpload;
    private String imageURL;
    private String tag;

    public GuideItem(String author, String title, String description, String timeUpload, String imageURL, String tag) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.timeUpload = timeUpload;
        this.imageURL = imageURL;
        this.tag = tag;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTimeUpload() {
        return timeUpload;
    }

    public String getImageURL() {
        return imageURL;
    }
    public String getTag() {
        return tag;
    }

}