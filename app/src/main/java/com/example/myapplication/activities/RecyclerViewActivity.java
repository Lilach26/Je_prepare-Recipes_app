package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapters.RecipeAdapter;
import com.example.myapplication.model.Recipe;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity
{
    private String chosenCategory;
    private ArrayList<Recipe> recipesArrayList;
    private RecyclerView recipesRecyclerView;
    private FirebaseFirestore db;
    private RecipeAdapter recipeAdapter;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        recipesRecyclerView = findViewById(R.id.recipesRecyclerView);
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uid = user.getUid();
        recipesArrayList = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(recipesArrayList);
        recipesRecyclerView.setAdapter(recipeAdapter);

        Intent intent = getIntent();
        //getting intent string extra from homeFragment, to find out which category the user chose (in cardView), and accordingly retrieve all the recipes
        chosenCategory = intent.getStringExtra("categoryNameKey");

        //function to get all documents of a specific category collection
        db.collection("Users").document(uid).collection(chosenCategory).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {
                        //save list of documents
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot documentSnapshot : list)
                        {
                            //convert each document to recipe object, so we can add it to recipe's arrayList
                            Recipe obj = documentSnapshot.toObject(Recipe.class);
                            recipesArrayList.add(obj);
                        }
                        //Notify adapter for the changes that have made
                        recipeAdapter.notifyDataSetChanged();
                    }
                });

        //function that deletes item from recipes recycler view, and update data inside fire-store db, onMove irrelevant for us
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                //save the document for delete to string, so we can get to it and delete it
                String documentForDelete = recipesArrayList.get(viewHolder.getAdapterPosition()).getRecipeName();
                db.collection("Users").document(uid).collection(chosenCategory).document(documentForDelete)
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Toast.makeText(RecyclerViewActivity.this, "Recipe deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                //notifying adapter for changes and remove the object from the arrayList
                recipesArrayList.remove(viewHolder.getAdapterPosition());
                recipeAdapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(recipesRecyclerView);
    }
}