package com.example.fastmart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class SellerMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = getSharedPreferences("pref", Context.MODE_PRIVATE);
        if (sp.getBoolean("darkMode", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.home, R.string.home); // Using existing strings for accessibility
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.seller_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.seller_home);
        }

        findViewById(R.id.fab_add_product).setOnClickListener(v -> {
            startActivity(new Intent(SellerMainActivity.this, AddProductActivity.class));
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        int id = item.getItemId();

        if (id == R.id.seller_home) {
            selectedFragment = new HomeFragment();
        } else if (id == R.id.seller_orders) {
            // selectedFragment = new OrderHistoryFragment();
        } else if (id == R.id.seller_account) {
            selectedFragment = new ProfileFragment();
        } else if (id == R.id.theme_toggle) {
            toggleTheme();
        } else if (id == R.id.logout) {
            logout();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.seller_container,
                    selectedFragment).commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void toggleTheme() {
        boolean isDarkMode = sp.getBoolean("darkMode", false);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("darkMode", !isDarkMode);
        editor.apply();
        recreate();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(SellerMainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
