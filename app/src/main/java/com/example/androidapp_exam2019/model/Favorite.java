package com.example.androidapp_exam2019.model;

import java.math.BigDecimal;

public class Favorite {
    private String id;
    private String urlImage;
    private String dressName;
    private BigDecimal dressPrice;
    private boolean available;
    private String dressId;
    private String customerId;

    public Favorite(String id, String urlImage, String dressName, BigDecimal dressPrice, boolean available, String dressId, String customerId) {
        this.id = id;
        this.urlImage = urlImage;
        this.dressName = dressName;
        this.dressPrice = dressPrice;
        this.available = available;
        this.dressId = dressId;
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getDressName() {
        return dressName;
    }

    public BigDecimal getDressPrice() {
        return dressPrice;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getDressId() {
        return dressId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
