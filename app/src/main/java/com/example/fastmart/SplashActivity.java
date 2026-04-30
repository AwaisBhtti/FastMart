package com.example.fastmart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    ImageView truck;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        truck = findViewById(R.id.truckImg);
        SharedPreferences sp = getSharedPreferences("pref", MODE_PRIVATE);
        anim = AnimationUtils.loadAnimation(this, R.anim.truckanim);
        truck.startAnimation(anim);

        new Handler().postDelayed(() -> {
            if (!sp.getBoolean("boardingShown", false)) {
                startActivity(new Intent(SplashActivity.this, onBoardingActivity.class));
            } else if (FirebaseAuth.getInstance().getCurrentUser() != null && sp.getBoolean("loggedIn", false)) {
                String accountType = sp.getString("accountType", "Buyer");
                if ("Seller".equalsIgnoreCase(accountType)) {
                    startActivity(new Intent(SplashActivity.this, SellerMainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finish();
        }, 3000);
    }
}
