package com.example.androidapp_exam2019.model;

import java.math.BigDecimal;
import java.util.Date;

public class Dress {
    private int id;
    private String urlPicture;
    private String dressName;
    private String describe;
    private BigDecimal price;
    private boolean available;
    private Date dateBeginAvailable;
    private Date dateEndAvailable;
    private int partnerId;
    private String partnerName;

    public Dress(int id, String urlPicture, String dressName, String describe, BigDecimal price, boolean available, Date dateBeginAvailable, Date dateEndAvailable, int partnerId, String partnerName) {
        this.id = id;
        this.urlPicture = urlPicture;
        this.dressName = dressName;
        this.describe = describe;
        this.price = price;
        this.available = available;
        this.dateBeginAvailable = dateBeginAvailable;
        this.dateEndAvailable = dateEndAvailable;
        this.partnerId = partnerId;
        this.partnerName = partnerName;
    }

    public int getId() {
        return id;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public String getDressName() {
        return dressName;
    }

    public String getDescribe() {
        return describe;
    }

    public BigDecimal getPrice() {
        return price;
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

    public int getPartnerId() {
        return partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }
}
