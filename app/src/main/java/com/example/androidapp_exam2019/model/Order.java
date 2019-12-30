package com.example.androidapp_exam2019.model;

import java.util.ArrayList;
import java.util.Date;

public class Order {

    private String id;
    private Date billingDate;
    private Date deliveryDate;
    private String billingAddress;
    private String deliveryAddress;
    private boolean isValid;
    private String customerId;
    private String customerName;
    private ArrayList<OrderLine> orderLines;

    public Order(String id, Date billingDate, Date deliveryDate, String billingAddress, String deliveryAddress, boolean isValid, String customerId, String customerName, ArrayList<OrderLine> orderLines) {
        this.id = id;
        this.billingDate = billingDate;
        this.deliveryDate = deliveryDate;
        this.billingAddress = billingAddress;
        this.deliveryAddress = deliveryAddress;
        this.isValid = isValid;
        this.customerId = customerId;
        this.customerName = customerName;
        this.orderLines = orderLines;
    }

    public String getId() {
        return id;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public ArrayList<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
    }
}
