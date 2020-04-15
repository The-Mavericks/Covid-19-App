package com.example.chatbot;

public class Item {
    private int image;
    private String description;
    private String title;
    private boolean expanded;

    public Item(String title, String description, int image) {
        this.description = description;
        this.title = title;
        this.image = image;
        expanded = false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
