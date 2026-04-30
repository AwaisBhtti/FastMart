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

import com.example.fastmart.repository.OrderRepository;

public class OrderHistoryFragment extends Fragment {

    private RecyclerView rvOrderHistory;
    private OrderRepository orderRepository;
    private OrderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);

        rvOrderHistory = view.findViewById(R.id.rvOrderHistory);
        rvOrderHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        orderRepository = new OrderRepository();
        SharedPreferences sp = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String sellerId = sp.getString("userId", "");

        if (!sellerId.isEmpty()) {
            orderRepository.fetchOrdersForSeller(sellerId);
        }

        orderRepository.getOrdersLiveData().observe(getViewLifecycleOwner(), orders -> {
            adapter = new OrderAdapter(orders);
            rvOrderHistory.setAdapter(adapter);
        });

        return view;
    }
}
