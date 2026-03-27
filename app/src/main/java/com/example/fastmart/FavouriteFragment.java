package com.example.fastmart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavouriteFragment extends Fragment {

    private RecyclerView rvFavorites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        rvFavorites = view.findViewById(R.id.rvFavorites);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        SharedPreferences sp = requireContext().getSharedPreferences("fav", Context.MODE_PRIVATE);
        Set<String> savedIds = sp.getStringSet("FAV_IDS", new HashSet<>());
        List<Product> favoritedProducts = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            String currentId = "" + i;
            if (savedIds.contains(currentId)) {
                Product p = createProductByPattern(i);
                favoritedProducts.add(p);
            }
        }
        FavouritesAdapter adapter = new FavouritesAdapter(favoritedProducts, getContext());
        rvFavorites.setAdapter(adapter);
        return view;
    }
    private Product createProductByPattern(int index) {
        String id = "" + index;
        int pattern = index % 4;

        if (pattern == 1) {
            return new Product(id,"RØDE PodMic", 199.99, 199.99, "Model: WH-1000XM4, Black", "Microphone", R.drawable.mic_rode);
        } else if (pattern == 2) {
            return new Product(id,"SONY Headphones", 399.99, 399.99, "Model: WH-1000XM5, Beige", "Headphone", R.drawable.headphones_beige);
        } else if (pattern == 3) {
            return new Product(id,"Google Nest Mini", 99.99, 99.99, "Model: WH-1000XM6, White", "Nest-Mini", R.drawable.nest_mini);
        } else {
            return new Product(id,"SONY Headphones", 399.99, 399.99, "Model: WH-1000XM5, Black", "Headphone", R.drawable.headphones_black);
        }
    }
}