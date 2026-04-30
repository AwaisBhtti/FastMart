package com.example.fastmart.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fastmart.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private DatabaseReference productsRef;
    private MutableLiveData<List<Product>> productsLiveData;

    public ProductRepository() {
        productsRef = FirebaseDatabase.getInstance().getReference("products");
        productsLiveData = new MutableLiveData<>();
        listenForProducts();
    }

    private void listenForProducts() {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> products = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    if (product != null) {
                        product.setId(postSnapshot.getKey());
                        products.add(product);
                    }
                }
                productsLiveData.postValue(products);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    public void addProduct(Product product) {
        String id = productsRef.push().getKey();
        product.setId(id);
        if (id != null) {
            productsRef.child(id).setValue(product);
        }
    }

    public LiveData<List<Product>> getProductsLiveData() {
        return productsLiveData;
    }
}
