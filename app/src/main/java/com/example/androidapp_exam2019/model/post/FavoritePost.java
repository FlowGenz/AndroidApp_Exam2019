package com.example.androidapp_exam2019.model.post;

public class FavoritePost {
    private String id;
    private String userId;
    private String dressId;

    public FavoritePost(String id, String userId, String dressId) {
        this.id = id;
        this.userId = userId;
        this.dressId = dressId;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getDressId() {
        return dressId;
    }
}
