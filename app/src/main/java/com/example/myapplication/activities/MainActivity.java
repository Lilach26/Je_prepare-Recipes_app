package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void logout_func(View view) {
            //clear all preferences so the user can insert new details
            SharedPreferences sharedPreferences = getSharedPreferences("MYKEY",0);
            //sharedPreferences = getSharedPreferences("KeyPassword",0);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            //remove
            editor.remove("email").apply();
            editor.remove("password").apply();

            editor.clear();
            editor.commit();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
     }

}