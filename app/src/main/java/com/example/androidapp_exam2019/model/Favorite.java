package com.example.androidapp_exam2019.model;

import android.net.Uri;

public class Favorite {
    private Uri uri;
    private String description;
    private Double price;

    public Favorite(Uri uri, String description, Double price) {
        this.uri = uri;
        this.description = description;
        this.price = price;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
