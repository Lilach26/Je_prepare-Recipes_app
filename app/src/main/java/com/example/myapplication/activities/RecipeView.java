package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.Internet;

import java.util.ArrayList;

public class RecipeView extends AppCompatActivity
{
    private TextView namePreview, descriptionPreview, ingredientsPreview, stepPreview, categoryPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        namePreview = findViewById(R.id.namePreview);
        descriptionPreview = findViewById(R.id.descriptionPreview);
        ingredientsPreview = findViewById(R.id.ingredientsPreview);
        stepPreview = findViewById(R.id.stepsPreview);
        categoryPreview = findViewById(R.id.categoryPreview);

        //get intent "putExtra" keys, for displaying each recipe's details
        Intent intent = getIntent();
        namePreview.setText(intent.getStringExtra("nameKey"));
        descriptionPreview.setText(intent.getStringExtra("descriptionKey"));
        ingredientsPreview.setText(intent.getStringExtra("ingredientsKey"));
        stepPreview.setText(intent.getStringExtra("stepsKey"));
        categoryPreview.setText(intent.getStringExtra("categoryKey"));
    }
}