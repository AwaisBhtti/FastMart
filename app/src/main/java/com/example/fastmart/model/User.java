package com.example.fastmart.model;

public class User {
    private String userId;
    private String name;
    private String address;
    private String gender;
    private String dateOfBirth;
    private String phone;
    private String country;
    private String accountType; // "Buyer" or "Seller"

    public User() {
        // Required for Firebase
    }

    public User(String userId, String name, String address, String gender, String dateOfBirth, String phone, String country, String accountType) {
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.country = country;
        this.accountType = accountType;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }
}
