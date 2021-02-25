package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private ArrayList<String> ingredientsArrayList;
    private ArrayAdapter<String> adapter;
    private Button addIngredientsBtn;
    private EditText ingredientsInput;
    private ListView ingredientsListView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShoppingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingFragment newInstance(String param1, String param2) {
        ShoppingFragment fragment = new ShoppingFragment();
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
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        ingredientsArrayList = new ArrayList<>();
        addIngredientsBtn = view.findViewById(R.id.addIngredientToListBtn);
        ingredientsInput = view.findViewById(R.id.ingredientInputText);
        ingredientsListView = view.findViewById(R.id.ingredientsListView);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, ingredientsArrayList);

        //add items to list view
        addIngredientsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                HashMap<String, String> ingredientsObj = new HashMap<>();
                ingredientsArrayList.add(ingredientsInput.getText().toString());
                ingredientsObj.put(ingredientsInput.getText().toString(), "");
                db.collection("Users").document(uid).collection("Shopping List").document("Ingredients").set(ingredientsObj);
                ingredientsInput.setText("");
                adapter.notifyDataSetChanged();
            }
        });

        ingredientsListView.setAdapter(adapter);

        //remove items from list view
        ingredientsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray checkedItems = ingredientsListView.getCheckedItemPositions();
                int count = ingredientsListView.getCount();

                for (int i = count - 1; i >= 0; i--)
                {
                    if (checkedItems.get(i))
                    {
                        adapter.remove(ingredientsArrayList.remove(i));
                        Toast.makeText(getActivity(), "Item deleted Successfully", Toast.LENGTH_LONG).show();
                    }
                }

                checkedItems.clear();
                adapter.notifyDataSetChanged();

                return false;
            }
        });

        return view;
    }
}