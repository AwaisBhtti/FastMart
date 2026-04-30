package com.example.fastmart;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastmart.model.CartItem;
import com.example.fastmart.model.Order;
import com.example.fastmart.repository.OrderRepository;
import com.example.fastmart.viewmodel.CartViewModel;

import java.util.List;

public class CartFragment extends Fragment implements CartAdapter.OnCartChangedListener {

    private RecyclerView rvCart;
    private TextView tvTotal;
    private Button btnCheckout;
    private CartAdapter adapter;
    private CartViewModel cartViewModel;
    private OrderRepository orderRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        rvCart = view.findViewById(R.id.rvCart);
        tvTotal = view.findViewById(R.id.tvCartTotal);
        btnCheckout = view.findViewById(R.id.btnCheckout);

        rvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        orderRepository = new OrderRepository();

        cartViewModel.getCartItemsLiveData().observe(getViewLifecycleOwner(), items -> {
            adapter = new CartAdapter(items, this);
            rvCart.setAdapter(adapter);
        });

        cartViewModel.getTotalAmountLiveData().observe(getViewLifecycleOwner(), total -> {
            tvTotal.setText(String.format("Total: $%.2f", total));
        });

        btnCheckout.setOnClickListener(v -> {
            List<CartItem> items = cartViewModel.getCartItemsLiveData().getValue();
            if (items == null || items.isEmpty()) {
                Toast.makeText(getContext(), "Cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            showConfirmationDialog(items);
        });

        return view;
    }

    private void showConfirmationDialog(List<CartItem> items) {
        new AlertDialog.Builder(getContext())
                .setTitle("Checkout Confirmation")
                .setMessage("Are you sure you want to checkout?")
                .setPositiveButton("Yes", (dialog, which) -> processCheckout(items))
                .setNegativeButton("No", null)
                .show();
    }

    private void processCheckout(List<CartItem> items) {
        StringBuilder summary = new StringBuilder("Order Summary:\n");
        double total = 0;
        for (CartItem item : items) {
            summary.append(item.getName()).append(" x").append(item.getQuantity())
                    .append(" ($").append(item.getPrice()).append(")\n");
            total += item.getPrice() * item.getQuantity();
        }
        summary.append("Total: $").append(String.format("%.2f", total));

        sendSMS(summary.toString());

        SharedPreferences sp = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String buyerId = sp.getString("userId", "");
        
        // Strictly following "orders/{sellerId}". 
        // In a real app, items might have different sellers. 
        // Here we assume a default seller or take the first item's seller.
        String sellerId = "default_seller"; // Should ideally come from product data
        
        Order order = new Order(null, sellerId, buyerId, items, total, System.currentTimeMillis());
        orderRepository.placeOrder(order);

        cartViewModel.clearCart();
        Toast.makeText(getContext(), "Order Placed!", Toast.LENGTH_SHORT).show();
    }

    private void sendSMS(String message) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 100);
        } else {
            try {
                String phoneNumber = "+923144536266";
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            } catch (Exception e) {
                Toast.makeText(getContext(), "SMS Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onQuantityChanged(String productId, int newQuantity) {
        cartViewModel.updateQuantity(productId, newQuantity);
    }

    @Override
    public void onRemoveItem(String productId) {
        cartViewModel.removeFromCart(productId);
    }
}
