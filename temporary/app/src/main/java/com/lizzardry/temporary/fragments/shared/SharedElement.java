package com.lizzardry.temporary.fragments.shared;

public class SharedElement {
    private String title;
    private String imageUrl;

    public SharedElement(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
