package com.example.fastmart;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rvDeals;
    private RecyclerView rvRecommended;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvDeals = view.findViewById(R.id.rvDeals);
        rvRecommended = view.findViewById(R.id.rvRecommended);

        List<Product> dealsList = new ArrayList<>();
        dealsList.add(new Product("RØDE PodMic", 108.20, 199.99, "Dynamic microphone, Speaker microphone", "Microphone", R.drawable.mic_rode));
        dealsList.add(new Product("SONY Headphones", 349.99, 399.99, "Premium Wireless Noise Cancelling", "Headphone", R.drawable.headphones_beige));
        dealsList.add(new Product("Google Nest Mini", 70.99, 99.99, "Smart speaker with Google Assistant", "Nest-Mini", R.drawable.nest_mini));

        rvDeals.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        DealsAdapter dealsAdapter = new DealsAdapter(dealsList);
        rvDeals.setAdapter(dealsAdapter);
        rvRecommended.setLayoutManager(new GridLayoutManager(getContext(), 2));
        List<Product> recommendedList = new ArrayList<>();
        for(int i=0; i<5;i++)
        {
            recommendedList.add(new Product("RØDE PodMic", 199.99, 199.99, "Model: WH-1000XM4, Black", "Microphone", R.drawable.mic_rode));
            recommendedList.add(new Product("SONY Headphones", 399.99, 399.99, "Model: WH-1000XM5, Beige", "Headphone", R.drawable.headphones_beige));
            recommendedList.add(new Product("Google Nest Mini", 99.99, 99.99, "Model: WH-1000XM6, White", "Nest-Mini", R.drawable.nest_mini));
            recommendedList.add(new Product("SONY Headphones", 399.99, 399.99, "Model: WH-1000XM5, Black", "Headphone", R.drawable.headphones_black));
        }
        RecommendedAdapter recAdapter = new RecommendedAdapter(recommendedList);
        rvRecommended.setAdapter(recAdapter);
        return view;
    }
}