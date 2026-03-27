package com.example.fastmart;

public class Product {
    private String title;
    private String category;
    private double price;
    private double originalPrice;
    private String description;
    private int imageResource;

    public Product(String title, double price, double originalPrice, String description, String category, int imageResource) {
        this.title = title;
        this.price = price;
        this.originalPrice = originalPrice;
        this.description = description;
        this.imageResource = imageResource;
        this.category=category;
    }

    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public double getOriginalPrice() { return originalPrice; }
    public String getDescription() { return description; }
    public int getImageResource() { return imageResource;}
    public String getCategory() { return category; }
}