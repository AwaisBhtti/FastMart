package com.example.fastmart.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fastmart.db";
    private static final int DATABASE_VERSION = 1;

    // Favourites table
    public static final String TABLE_FAVOURITES = "favourites";
    public static final String COL_FAV_ID = "id";
    public static final String COL_FAV_PRODUCT_ID = "productId";
    public static final String COL_FAV_NAME = "name";
    public static final String COL_FAV_PRICE = "price";

    // Cart table
    public static final String TABLE_CART = "cart";
    public static final String COL_CART_ID = "id";
    public static final String COL_CART_PRODUCT_ID = "productId";
    public static final String COL_CART_NAME = "name";
    public static final String COL_CART_PRICE = "price";
    public static final String COL_CART_QUANTITY = "quantity";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createFavouritesTable = "CREATE TABLE " + TABLE_FAVOURITES + " (" +
                COL_FAV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FAV_PRODUCT_ID + " TEXT, " +
                COL_FAV_NAME + " TEXT, " +
                COL_FAV_PRICE + " REAL)";
        db.execSQL(createFavouritesTable);

        String createCartTable = "CREATE TABLE " + TABLE_CART + " (" +
                COL_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CART_PRODUCT_ID + " TEXT, " +
                COL_CART_NAME + " TEXT, " +
                COL_CART_PRICE + " REAL, " +
                COL_CART_QUANTITY + " INTEGER)";
        db.execSQL(createCartTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }
}
