package com.example.fastmart;

public class Product {
    private String title;
    String id="";
    private String category;
    private double price;
    private double originalPrice;
    private String description;
    private int imageResource;
    private boolean isFavourite=false;

    public Product(String title, double price, double originalPrice, String description, String category, int imageResource) {
        this.title = title;
        this.price = price;
        this.originalPrice = originalPrice;
        this.description = description;
        this.imageResource = imageResource;
        this.category=category;
    }
    public Product(String id, String title, double price, double originalPrice, String description, String category, int imageResource) {
        this.title = title;
        this.price = price;
        this.originalPrice = originalPrice;
        this.description = description;
        this.imageResource = imageResource;
        this.category=category;
        this.id=id;
    }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public double getOriginalPrice() { return originalPrice; }
    public String getDescription() { return description; }
    public int getImageResource() { return imageResource;}
    public String getCategory() { return category; }
    public boolean isFavourite() { return isFavourite; }
    public void setFavourite(boolean favourite) { isFavourite = favourite; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}