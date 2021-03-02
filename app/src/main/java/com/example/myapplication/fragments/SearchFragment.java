package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private EditText ingredientsInput, categoryInput;
    private Button searchButton;
    private TextView resultsTextView;
    private HashMap<String, ArrayList<String>> ingredientsForRecipe;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ingredientsInput = view.findViewById(R.id.ingredientsSearchInput);
        categoryInput = view.findViewById(R.id.categoryInput);
        searchButton = view.findViewById(R.id.searchButton);
        resultsTextView = view.findViewById(R.id.resultTextView);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                db = FirebaseFirestore.getInstance();
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                String category = categoryInput.getText().toString();
                CollectionReference reference = db.collection("Users").document(uid).collection(category);
                reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            ingredientsForRecipe = new HashMap<>();
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Recipe recipe = document.toObject(Recipe.class);
                                ingredientsForRecipe.put(recipe.getRecipeName(),recipe.getIngredients());
                            }
                            String ingredientsStr = ingredientsInput.getText().toString();
                            String[] arrayOfIngredientsSearch = ingredientsStr.split(",");
                            ArrayList<String> splitIngredientsSearch = new ArrayList<>();

                            for (int i = 0; i < arrayOfIngredientsSearch.length; i++)
                            {
                                splitIngredientsSearch.add(arrayOfIngredientsSearch[i]);
                            }

                            boolean flag = false;
                            resultsTextView.setText("");
                            for (Map.Entry<String,ArrayList<String>> entry : ingredientsForRecipe.entrySet())
                            {
                                ArrayList<String> temp = entry.getValue();
                                if(temp.retainAll(splitIngredientsSearch))
                                {
                                    if(!temp.isEmpty()) {
                                        resultsTextView.setText(resultsTextView.getText().toString() + "\n" + entry.getKey());
                                        flag = true;
                                    }
                                }
                            }
                            if(!flag)
                            {
                                resultsTextView.setText("not found");
                            }
                        }
                    }
                });
            }
        });

        return view;
    }
}