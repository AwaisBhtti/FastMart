package com.example.fastmart.model;

public class Product {
    private String id;
    private String title;
    private String category;
    private double price;
    private double originalPrice;
    private String description;
    private String imageUrl;
    private String sellerId;

    public Product() {
        // Required for Firebase
    }

    public Product(String id, String title, String category, double price, double originalPrice, String description, String imageUrl, String sellerId) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.originalPrice = originalPrice;
        this.description = description;
        this.imageUrl = imageUrl;
        this.sellerId = sellerId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public double getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(double originalPrice) { this.originalPrice = originalPrice; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
}
