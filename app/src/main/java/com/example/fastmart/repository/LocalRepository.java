package com.example.fastmart.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fastmart.database.DatabaseHelper;
import com.example.fastmart.model.CartItem;
import com.example.fastmart.model.Product;

import java.util.ArrayList;
import java.util.List;

public class LocalRepository {
    private DatabaseHelper dbHelper;

    public LocalRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Favourites
    public void addFavourite(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_FAV_PRODUCT_ID, product.getId());
        values.put(DatabaseHelper.COL_FAV_NAME, product.getTitle());
        values.put(DatabaseHelper.COL_FAV_PRICE, product.getPrice());
        db.insert(DatabaseHelper.TABLE_FAVOURITES, null, values);
    }

    public void removeFavourite(String productId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_FAVOURITES, DatabaseHelper.COL_FAV_PRODUCT_ID + "=?", new String[]{productId});
    }

    public boolean isFavourite(String productId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_FAVOURITES, null, DatabaseHelper.COL_FAV_PRODUCT_ID + "=?", new String[]{productId}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Cart
    public void addToCart(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_CART_PRODUCT_ID, product.getId());
        values.put(DatabaseHelper.COL_CART_NAME, product.getTitle());
        values.put(DatabaseHelper.COL_CART_PRICE, product.getPrice());
        values.put(DatabaseHelper.COL_CART_QUANTITY, 1);
        db.insert(DatabaseHelper.TABLE_CART, null, values);
    }

    public void updateCartQuantity(String productId, int quantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_CART_QUANTITY, quantity);
        db.update(DatabaseHelper.TABLE_CART, values, DatabaseHelper.COL_CART_PRODUCT_ID + "=?", new String[]{productId});
    }

    public void removeFromCart(String productId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_CART, DatabaseHelper.COL_CART_PRODUCT_ID + "=?", new String[]{productId});
    }

    public void clearCart() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_CART, null, null);
    }
}
