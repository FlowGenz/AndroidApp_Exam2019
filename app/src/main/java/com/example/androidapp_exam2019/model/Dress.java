package com.example.androidapp_exam2019.model;

import java.math.BigDecimal;
import java.util.Date;

public class Dress {
    private String id;
    private String urlPicture;
    private String dressName;
    private String description;
    private BigDecimal price;
    private String size;
    private boolean available;
    private Date dateBeginAvailable;
    private Date dateEndAvailable;
    private String partnerId;
    private String partnerName;

    public Dress(String id, String urlPicture, String dressName, String description, BigDecimal price, String size, boolean available, Date dateBeginAvailable, Date dateEndAvailable, String partnerId, String partnerName) {
        this.id = id;
        this.urlPicture = urlPicture;
        this.dressName = dressName;
        this.description = description;
        this.price = price;
        this.size = size;
        this.available = available;
        this.dateBeginAvailable = dateBeginAvailable;
        this.dateEndAvailable = dateEndAvailable;
        this.partnerId = partnerId;
        this.partnerName = partnerName;
    }

    public String getId() {
        return id;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public String getDressName() {
        return dressName;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public boolean isAvailable() {
        return available;
    }

    public Date getDateBeginAvailable() {
        return dateBeginAvailable;
    }

    public Date getDateEndAvailable() {
        return dateEndAvailable;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }
}
