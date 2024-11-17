package com.example.project_palm_on;

public class item_artikel {
    private String author;
    private String title;
    private String description;
    private String timeUpload;
    private String imageURL;

    public item_artikel(String author, String title, String description, String timeUpload, String imageURL) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.timeUpload = timeUpload;
        this.imageURL = imageURL;
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

}
