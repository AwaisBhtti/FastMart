package com.example.fastmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnv=findViewById(R.id.bottommenu);
        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
        }

        bnv.setOnItemSelectedListener(item->{
            Fragment f=null;
            if(item.getItemId()==R.id.home)
            {
                f=new HomeFragment();
            }
            else if(item.getItemId()==R.id.cart)
            {
                f=new CartFragment();
            }
            else if(item.getItemId()==R.id.search)
            {
                f=new SearchFragment();
            }
            else if(item.getItemId()==R.id.favourites)
            {
                f=new FavouriteFragment();
            }
            else if(item.getItemId()==R.id.profile)
            {
                f=new ProfileFragment();
            }
            if(f!=null)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, f).commit();
            }
            return true;
        });
    }
}