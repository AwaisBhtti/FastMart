package com.example.fastmart.viewmodel;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fastmart.database.DatabaseHelper;
import com.example.fastmart.model.Product;
import com.example.fastmart.repository.LocalRepository;

import java.util.ArrayList;
import java.util.List;

public class FavouritesViewModel extends AndroidViewModel {
    private LocalRepository localRepository;
    private MutableLiveData<List<Product>> favouritesLiveData;
    private DatabaseHelper dbHelper;

    public FavouritesViewModel(@NonNull Application application) {
        super(application);
        localRepository = new LocalRepository(application);
        dbHelper = new DatabaseHelper(application);
        favouritesLiveData = new MutableLiveData<>();
        loadFavourites();
    }

    public void loadFavourites() {
        List<Product> items = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_FAVOURITES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FAV_PRODUCT_ID)));
                product.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FAV_NAME)));
                product.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FAV_PRICE)));
                // Note: Minimal data stored in SQLite as per common practice, or store all. 
                // Using enough to display in list.
                items.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        favouritesLiveData.postValue(items);
    }

    public void toggleFavourite(Product product) {
        if (localRepository.isFavourite(product.getId())) {
            localRepository.removeFavourite(product.getId());
        } else {
            localRepository.addFavourite(product);
        }
        loadFavourites();
    }

    public boolean isFavourite(String productId) {
        return localRepository.isFavourite(productId);
    }

    public LiveData<List<Product>> getFavouritesLiveData() {
        return favouritesLiveData;
    }
}
