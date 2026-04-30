package com.example.fastmart.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fastmart.model.Product;
import com.example.fastmart.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository productRepository;
    private LiveData<List<Product>> productsLiveData;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository();
        productsLiveData = productRepository.getProductsLiveData();
    }

    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    public LiveData<List<Product>> getProductsLiveData() {
        return productsLiveData;
    }
}
