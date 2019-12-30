package com.example.androidapp_exam2019.model;

public class FavoriteDress {
    private Boolean isFavorite;
    private String favoriteId;

    public FavoriteDress(Boolean isFavorite, String favoriteId) {
        this.isFavorite = isFavorite;
        this.favoriteId = favoriteId;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public String getFavoriteId() {
        return favoriteId;
    }
}
