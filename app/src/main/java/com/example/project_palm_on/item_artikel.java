package com.example.project_palm_on;

public class item_artikel {
    private String author;
    private String title;
    private String description;
    private String timeUpload;
    private int imageResourceId;
    private int iconResourceId;

    public item_artikel(String author, String title, String description, String timeUpload, int imageResourceId, int iconResourceId) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.timeUpload = timeUpload;
        this.imageResourceId = imageResourceId;
        this.iconResourceId = iconResourceId;
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

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }
}
