package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.activities.MainActivity;
import com.example.myapplication.logic.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRecipeFragment extends Fragment {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private EditText recipeName, ingredients, steps, description, image;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddRecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRecipeFragment newInstance(String param1, String param2) {
        AddRecipeFragment fragment = new AddRecipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_recipe, container, false);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        recipeName = view.findViewById(R.id.recipeNameText);
        description = view.findViewById(R.id.descriptionText);
        ingredients = view.findViewById(R.id.ingredientsText);
        steps = view.findViewById(R.id.stepsText);
        image = view.findViewById(R.id.imageText);

        String recipeNameStr = recipeName.getText().toString();
        String descriptionStr = description.getText().toString();
        String ingredientsStr = ingredients.getText().toString();
        String stepsStr = steps.getText().toString();
        String imageStr = image.getText().toString();

        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                ArrayList<String> ingredients = new ArrayList<>();

                //split the ingredients by ,
                //and to add to arraylist
                ingredients.add(ingredientsStr);

                //make spinner work for category
                Recipe recipe = new Recipe(recipeNameStr, descriptionStr, ingredients, stepsStr, "bla", "image");
                //update the category that user chose
                db.collection("Users").document(uid).collection("category").document(recipe.getRecipeName()).set(recipe);

            }
        });
        return view;

    }

}