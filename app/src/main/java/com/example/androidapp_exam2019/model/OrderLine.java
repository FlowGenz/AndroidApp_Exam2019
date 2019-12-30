package com.example.androidapp_exam2019.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderLine {
    private String id;
    private Date dateBeginLocation;
    private Date dateEndLocation;
    private BigDecimal finalPrice;
    private String dressName;
    private boolean isDressAvailable;
    private String dressUrlImage;
    private String dressOrderId;
    private String dressId;

    public OrderLine(String id, Date dateBeginLocation, Date dateEndLocation, BigDecimal finalPrice, String dressName, boolean isDressAvailable, String dressUrlImage, String dressOrderId, String dressId) {
        this.id = id;
        this.dateBeginLocation = dateBeginLocation;
        this.dateEndLocation = dateEndLocation;
        this.finalPrice = finalPrice;
        this.dressName = dressName;
        this.isDressAvailable = isDressAvailable;
        this.dressUrlImage = dressUrlImage;
        this.dressOrderId = dressOrderId;
        this.dressId = dressId;
    }

    public String getId() {
        return id;
    }

    public Date getDateBeginLocation() {
        return dateBeginLocation;
    }

    public Date getDateEndLocation() {
        return dateEndLocation;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public String getDressName() {
        return dressName;
    }

    public boolean isDressAvailable() {
        return isDressAvailable;
    }

    public String getDressUrlImage() {
        return dressUrlImage;
    }

    public String getDressOrderId() {
        return dressOrderId;
    }

    public String getDressId() {
        return dressId;
    }
}
