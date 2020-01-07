package com.example.androidapp_exam2019.model;

public class Customer {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String customerPassword;
    private String email;
    private String phoneNumber;
    private String customerAddress;
    private Integer loyaltyPoints;

    public Customer(String id, String firstName, String lastName, String username, String customerPassword, String email, String phoneNumber, String customerAddress, Integer loyaltyPoints) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.customerPassword = customerPassword;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.customerAddress = customerAddress;
        this.loyaltyPoints = loyaltyPoints;
    }

    public Customer() {

    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }
}
