package com.example.fastmart;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        ImageView img = findViewById(R.id.pImg);
        TextView nameTxt = findViewById(R.id.pName);
        TextView priceTxt = findViewById(R.id.pPrice);
        TextView descTxt = findViewById(R.id.pDesc);
        Button btnBuy = findViewById(R.id.btnBuy);
        String name = getIntent().getStringExtra("NAME");
        String priceStr = getIntent().getStringExtra("PRICE");
        String desc = getIntent().getStringExtra("DESC");
        int imgRes = getIntent().getIntExtra("IMG", 0);
        String id = getIntent().getStringExtra("ID");
        String category = getIntent().getStringExtra("CATEGORY");
        nameTxt.setText(name);
        priceTxt.setText(priceStr);
        descTxt.setText(desc);
        img.setImageResource(imgRes);
        btnBuy.setOnClickListener(v -> showConfirmationDialog(name, priceStr,category, desc, imgRes, id));
    }

    private void showConfirmationDialog(String name, String priceStr,String category, String desc, int imgRes, String id) {
        new AlertDialog.Builder(this)
                .setTitle("Add to Cart")
                .setMessage("Are you sure you want to add " + name + " to your cart?")
                .setPositiveButton("Add", (dialog, which) -> {
            double priceValue = 0;
            try {
                priceValue = Double.parseDouble(priceStr.replace("$", ""));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            Product productToAdd = new Product(name, priceValue, priceValue, desc, category, imgRes);
            productToAdd.setId(id);
            CartManager.addItem(productToAdd);
            Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
        })
                .setNegativeButton("Cancel", null)
                .show();
    }
}