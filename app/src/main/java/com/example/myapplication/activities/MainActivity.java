package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.R;
import com.example.myapplication.fragments.AddRecipeFragment;
import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.fragments.InternetFragment;
import com.example.myapplication.fragments.SearchFragment;
import com.example.myapplication.fragments.ShoppingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
{
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize bottom navigation view
        bottomNavigationView = findViewById(R.id.bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    //set click listener for which item selected in the bottom navigation view, then begin transaction for specific fragment
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            Fragment selected = null;
            switch (item.getItemId())
            {
                case(R.id.home_item):
                    selected = new HomeFragment();
                    break;
                case(R.id.add_item):
                    selected = new AddRecipeFragment();
                    break;
                case (R.id.search_item):
                    selected = new SearchFragment();
                    break;
                case(R.id.internet_item):
                    selected = new InternetFragment();
                    break;
                case(R.id.shopping_cart_item):
                    selected = new ShoppingFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selected).commit();
            return true;
        }
    };

    //LogOut button, switch back to Login page
    public void switchToLogin()
    {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}