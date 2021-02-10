package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.logic.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;
import java.util.HashMap;

public class AddRecipe extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
    }


    public void addRecipeFunc(View view) {

        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        HashMap<String, String> ingredients = new HashMap<>();
        ingredients.put("Banana", "3");

        Recipe recipe = new Recipe("Beef", "bla", ingredients, "bla", "bla", "bla");
        db.collection("Users").document(uid).collection("Recipes").document(recipe.getRecipeName()).set(recipe);

    }
}