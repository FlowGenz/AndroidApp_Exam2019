package com.example.androidapp_exam2019.model;

import java.math.BigDecimal;

public class Favorite {
    private Integer id;
    private String urlImage;
    private String dressName;
    private BigDecimal dressPrice;
    private Integer dressId;
    private Integer customerId;

    public Favorite(Integer id, String urlImage, String dressName, BigDecimal dressPrice, Integer dressId, Integer customerId) {
        this.id = id;
        this.urlImage = urlImage;
        this.dressName = dressName;
        this.dressPrice = dressPrice;
        this.dressId = dressId;
        this.customerId = customerId;
    }

    public Integer getId() {
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

    public Integer getDressId() {
        return dressId;
    }

    public Integer getCustomerId() {
        return customerId;
    }
}
