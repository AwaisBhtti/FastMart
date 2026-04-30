package com.example.fastmart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastmart.model.Product;
import com.example.fastmart.viewmodel.CartViewModel;
import com.example.fastmart.viewmodel.FavouritesViewModel;

import java.util.List;

public class FavouriteFragment extends Fragment implements FavouritesAdapter.OnFavRemovedListener {

    private RecyclerView rvFavorites;
    private FavouritesViewModel favouritesViewModel;
    private CartViewModel cartViewModel;
    private FavouritesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        rvFavorites = view.findViewById(R.id.rvFavorites);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        favouritesViewModel = new ViewModelProvider(this).get(FavouritesViewModel.class);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        favouritesViewModel.getFavouritesLiveData().observe(getViewLifecycleOwner(), products -> {
            adapter = new FavouritesAdapter(products, this);
            rvFavorites.setAdapter(adapter);
        });

        return view;
    }

    @Override
    public void onRemove(Product product) {
        favouritesViewModel.toggleFavourite(product);
        Toast.makeText(getContext(), "Removed from Favourites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddToCart(Product product) {
        cartViewModel.addToCart(product);
        Toast.makeText(getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
    }
}
