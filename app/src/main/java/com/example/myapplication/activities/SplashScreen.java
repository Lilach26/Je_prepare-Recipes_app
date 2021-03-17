package com.example.myapplication.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.example.myapplication.R;

public class SplashScreen extends AppCompatActivity
{
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //setting splash screen, will disappear after 1500 Millis
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);

                finish();
            }
        }, 1500);
    }
}