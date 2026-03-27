package com.example.fastmart;

import android.Manifest;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartFragment extends Fragment implements CartAdapter.OnCartChangedListener {

    private RecyclerView rvCart;
    private TextView tvTotal;
    private Button btnCheckout;
    private CartAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        rvCart = view.findViewById(R.id.rvCart);
        tvTotal = view.findViewById(R.id.tvCartTotal);
        btnCheckout = view.findViewById(R.id.btnCheckout);

        rvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(CartManager.getCartItems(), this);
        rvCart.setAdapter(adapter);

        updateTotalText();

        btnCheckout.setOnClickListener(v -> showConfirmationDialog());

        return view;
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Checkout Confirmation")
                .setMessage("Are you sure you want to checkout with bill of " + tvTotal.getText().toString().trim() + "?")
                .setPositiveButton("Yes", (dialog, which) -> sendSMS(tvTotal.getText().toString().trim()))
                .setNegativeButton("No", null)
                .show();
    }

    private void updateTotalText() {
        tvTotal.setText(String.format("Total: $%.2f", CartManager.calculateTotal()));
    }

    @Override
    public void onCartUpdated() { updateTotalText(); }

    private void sendSMS(String total) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 100);
        }
        else {
            try {
                String phoneNumber = "+923144536266";
                String message = total + " bill done.";
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                CartManager.clearCart();
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                updateTotalText();
                Toast.makeText(getContext(), "Order Placed & SMS Sent!", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(getContext(), "SMS Failed. Check Permissions.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}