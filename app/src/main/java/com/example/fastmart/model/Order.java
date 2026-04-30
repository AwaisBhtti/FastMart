package com.example.fastmart.model;

import java.util.List;

public class Order {
    private String orderId;
    private String sellerId;
    private String buyerId;
    private List<CartItem> items;
    private double totalAmount;
    private long timestamp;

    public Order() {}

    public Order(String orderId, String sellerId, String buyerId, List<CartItem> items, double totalAmount, long timestamp) {
        this.orderId = orderId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.timestamp = timestamp;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
    public String getBuyerId() { return buyerId; }
    public void setBuyerId(String buyerId) { this.buyerId = buyerId; }
    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
