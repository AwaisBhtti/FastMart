package com.example.fastmart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class onBoardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_onboarding);
        Button btnGS=findViewById(R.id.btnGetStarted);
        SharedPreferences sp=getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor ed=sp.edit();
        btnGS.setOnClickListener(v->{
            ed.putBoolean("boardingShown",true);
            ed.commit();
            startActivity(new Intent(onBoardingActivity.this,LoginActivity.class));
            finish();
        });
    }
}