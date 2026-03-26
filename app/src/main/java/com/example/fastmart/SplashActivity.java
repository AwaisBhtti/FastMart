package com.example.fastmart;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView truck = findViewById(R.id.truckImg);
        SharedPreferences sp=getSharedPreferences("pref",MODE_PRIVATE);
        Animation anim= AnimationUtils.loadAnimation(this,R.anim.truckanim);
        truck.startAnimation(anim);
        new Handler().postDelayed(() -> {
            if(!sp.getBoolean("boardingShown",false))
            {
                startActivity(new Intent(SplashActivity.this, onBoardingActivity.class));
            }
            else if(sp.getBoolean("loggedIn",false))
            {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
            else
            {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            }
            finish();
        }, 3000);
    }
}