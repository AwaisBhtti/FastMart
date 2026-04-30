package com.example.fastmart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fastmart.model.Product;
import com.example.fastmart.viewmodel.ProductViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class AddProductActivity extends AppCompatActivity {

    private TextInputEditText etName, etPrice, etDescription, etCategory;
    private Button btnSubmit;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        etName = findViewById(R.id.etProductName);
        etPrice = findViewById(R.id.etProductPrice);
        etDescription = findViewById(R.id.etProductDescription);
        etCategory = findViewById(R.id.etProductCategory);
        btnSubmit = findViewById(R.id.btnSubmitProduct);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        SharedPreferences sp = getSharedPreferences("pref", Context.MODE_PRIVATE);
        String sellerId = sp.getString("userId", "");

        btnSubmit.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String priceStr = etPrice.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String category = etCategory.getText().toString().trim();

            if (name.isEmpty() || priceStr.isEmpty() || description.isEmpty() || category.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceStr);
            Product product = new Product(null, name, category, price, price, description, null, sellerId);
            productViewModel.addProduct(product);
            Toast.makeText(this, "Product Added", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
