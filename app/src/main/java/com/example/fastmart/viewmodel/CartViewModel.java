package com.example.fastmart.viewmodel;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fastmart.database.DatabaseHelper;
import com.example.fastmart.model.CartItem;
import com.example.fastmart.model.Product;
import com.example.fastmart.repository.LocalRepository;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private LocalRepository localRepository;
    private MutableLiveData<List<CartItem>> cartItemsLiveData;
    private MutableLiveData<Double> totalAmountLiveData;
    private DatabaseHelper dbHelper;

    public CartViewModel(@NonNull Application application) {
        super(application);
        localRepository = new LocalRepository(application);
        dbHelper = new DatabaseHelper(application);
        cartItemsLiveData = new MutableLiveData<>();
        totalAmountLiveData = new MutableLiveData<>();
        loadCartItems();
    }

    public void loadCartItems() {
        List<CartItem> items = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_CART, null, null, null, null, null, null);

        double total = 0;
        if (cursor.moveToFirst()) {
            do {
                CartItem item = new CartItem();
                item.setId(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CART_ID))));
                item.setProductId(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CART_PRODUCT_ID)));
                item.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CART_NAME)));
                item.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CART_PRICE)));
                item.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CART_QUANTITY)));
                items.add(item);
                total += item.getPrice() * item.getQuantity();
            } while (cursor.moveToNext());
        }
        cursor.close();
        cartItemsLiveData.postValue(items);
        totalAmountLiveData.postValue(total);
    }

    public void addToCart(Product product) {
        localRepository.addToCart(product);
        loadCartItems();
    }

    public void updateQuantity(String productId, int quantity) {
        if (quantity <= 0) {
            localRepository.removeFromCart(productId);
        } else {
            localRepository.updateCartQuantity(productId, quantity);
        }
        loadCartItems();
    }

    public void removeFromCart(String productId) {
        localRepository.removeFromCart(productId);
        loadCartItems();
    }

    public void clearCart() {
        localRepository.clearCart();
        loadCartItems();
    }

    public LiveData<List<CartItem>> getCartItemsLiveData() {
        return cartItemsLiveData;
    }

    public LiveData<Double> getTotalAmountLiveData() {
        return totalAmountLiveData;
    }
}
