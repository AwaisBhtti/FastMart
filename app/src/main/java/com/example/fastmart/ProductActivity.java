package com.example.fastmart;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fastmart.model.Product;
import com.example.fastmart.viewmodel.CartViewModel;

public class ProductActivity extends AppCompatActivity {

    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        ImageView img = findViewById(R.id.pImg);
        TextView nameTxt = findViewById(R.id.pName);
        TextView priceTxt = findViewById(R.id.pPrice);
        TextView descTxt = findViewById(R.id.pDesc);
        Button btnBuy = findViewById(R.id.btnBuy);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        String id = getIntent().getStringExtra("ID");
        String name = getIntent().getStringExtra("NAME");
        String priceStr = getIntent().getStringExtra("PRICE");
        String desc = getIntent().getStringExtra("DESC");
        String category = getIntent().getStringExtra("CATEGORY");
        // For simplicity using placeholder, real app would use Glide with imageUrl
        img.setImageResource(R.drawable.nest_mini);

        nameTxt.setText(name);
        priceTxt.setText(priceStr);
        descTxt.setText(desc);

        btnBuy.setOnClickListener(v -> showConfirmationDialog(id, name, priceStr, category, desc));
    }

    private void showConfirmationDialog(String id, String name, String priceStr, String category, String desc) {
        new AlertDialog.Builder(this)
                .setTitle("Add to Cart")
                .setMessage("Are you sure you want to add " + name + " to your cart?")
                .setPositiveButton("Add", (dialog, which) -> {
                    double priceValue = 0;
                    try {
                        priceValue = Double.parseDouble(priceStr.replace("$", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Product productToAdd = new Product(id, name, category, priceValue, priceValue, desc, null, null);
                    cartViewModel.addToCart(productToAdd);
                    Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
