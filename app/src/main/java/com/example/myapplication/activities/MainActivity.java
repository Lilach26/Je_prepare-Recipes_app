package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch(item.getItemId())
            {
                case(R.id.add_item):
                    Intent intent = new Intent(MainActivity.this, AddRecipe.class);
                    startActivity(intent);
            }
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selected).commit();
            return true;
        }
    };

    public void logout_func(View view) {
            //clear all preferences so the user can insert new details
            SharedPreferences sharedPreferences = getSharedPreferences("LOGKEY",0);
            //sharedPreferences = getSharedPreferences("KeyPassword",0);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            //remove
            editor.remove("KeyEmail").apply();
            editor.remove("KeyPassword").apply();

            //editor.clear();
            //editor.commit();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
     }

}