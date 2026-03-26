package com.example.fastmart;

import android.content.DialogInterface;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.telephony.SmsManager;
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
        String price = getIntent().getStringExtra("PRICE");
        String desc = getIntent().getStringExtra("DESC");
        int imgRes = getIntent().getIntExtra("IMG", 0);

        nameTxt.setText(name);
        priceTxt.setText(price);
        descTxt.setText(desc);
        img.setImageResource(imgRes);
        btnBuy.setOnClickListener(v -> showConfirmationDialog(name));
    }

    private void showConfirmationDialog(String productName) {
        new AlertDialog.Builder(this)
                .setTitle("Buy Now")
                .setMessage("Are you sure you want to buy " + productName + "?")
                .setPositiveButton("Buy", (dialog, which) -> sendSMS(productName))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void sendSMS(String productName) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 100);
        }
        else {
            try {
                String phoneNumber = "+923144536266";
                String message = productName + " bought";

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);

                Toast.makeText(this, "Order Placed & SMS Sent!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "SMS Failed. Check Permissions.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}