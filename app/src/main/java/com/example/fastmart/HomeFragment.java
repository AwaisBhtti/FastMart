package com.example.fastmart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastmart.model.Product;
import com.example.fastmart.viewmodel.FavouritesViewModel;
import com.example.fastmart.viewmodel.ProductViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rvDeals;
    private RecyclerView rvRecommended;
    private ProductViewModel productViewModel;
    private FavouritesViewModel favouritesViewModel;
    private TextView tvHello;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvDeals = view.findViewById(R.id.rvDeals);
        rvRecommended = view.findViewById(R.id.rvRecommended);
        tvHello = view.findViewById(R.id.tvHello);

        SharedPreferences sp = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String name = sp.getString("name", "User");
        tvHello.setText(getString(R.string.hello_user, name));

        rvDeals.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvRecommended.setLayoutManager(new GridLayoutManager(getContext(), 2));

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        favouritesViewModel = new ViewModelProvider(this).get(FavouritesViewModel.class);

        productViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), products -> {
            RecommendedAdapter adapter = new RecommendedAdapter(products, favouritesViewModel);
            rvRecommended.setAdapter(adapter);
            
            if (!products.isEmpty()) {
                DealsAdapter dealsAdapter = new DealsAdapter(products);
                rvDeals.setAdapter(dealsAdapter);
            }
        });

        return view;
    }
}
