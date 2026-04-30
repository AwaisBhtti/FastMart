package com.example.fastmart.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fastmart.model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private DatabaseReference ordersRef;
    private MutableLiveData<List<Order>> ordersLiveData;

    public OrderRepository() {
        ordersRef = FirebaseDatabase.getInstance().getReference("orders");
        ordersLiveData = new MutableLiveData<>();
    }

    public void placeOrder(Order order) {
        // Requirements say: Store order in Firebase under: "orders/{sellerId}"
        // However, a single order might have items from multiple sellers.
        // For simplicity, we can store it by orderId and maybe index by sellerId if needed.
        // But following instructions strictly: "orders/{sellerId}"
        
        // If the order has items from multiple sellers, this structure is tricky.
        // Assuming one seller per order for now or just using a push ID.
        String sellerId = order.getSellerId();
        String orderId = ordersRef.child(sellerId).push().getKey();
        order.setOrderId(orderId);
        ordersRef.child(sellerId).child(orderId).setValue(order);
    }

    public void fetchOrdersForSeller(String sellerId) {
        ordersRef.child(sellerId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Order> orders = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Order order = postSnapshot.getValue(Order.class);
                    if (order != null) {
                        orders.add(order);
                    }
                }
                ordersLiveData.postValue(orders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public LiveData<List<Order>> getOrdersLiveData() {
        return ordersLiveData;
    }
}
