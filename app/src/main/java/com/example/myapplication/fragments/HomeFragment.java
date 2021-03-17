package com.example.myapplication.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.activities.MainActivity;
import com.example.myapplication.activities.RecyclerViewActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CardView beefCard, dairyCard, fishCard, veganCard, cocktailsCard, dessertsCard;
    private String chosenCard;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button logoutButton = view.findViewById(R.id.logOutButton);

        beefCard = (CardView) view.findViewById(R.id.beef_card);
        dairyCard = (CardView) view.findViewById(R.id.dairy_card);
        fishCard = (CardView) view.findViewById(R.id.fish_card);
        veganCard = (CardView) view.findViewById(R.id.vegan_card);
        cocktailsCard = (CardView) view.findViewById(R.id.cocktails_card);
        dessertsCard = (CardView) view.findViewById(R.id.desserts_card);

        //set click listeners for each cardView to figure out which category the user wants to open
        beefCard.setOnClickListener(this);
        dairyCard.setOnClickListener(this);
        fishCard.setOnClickListener(this);
        veganCard.setOnClickListener(this);
        cocktailsCard.setOnClickListener(this);
        dessertsCard.setOnClickListener(this);

        //Log out button, uses shared preferences remove user's details (which used for keeping him signed in, even if he closes the app)
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //clear all preferences so the user can insert new details
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LOGKEY",0);
                //sharedPreferences = getSharedPreferences("KeyPassword",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //remove
                editor.remove("KeyEmail").apply();
                editor.remove("KeyPassword").apply();

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.switchToLogin();
            }
        });

        return view;
    }

    //click listener function for the cardView clicks, to store in String variable which category were chosen
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.beef_card:
                chosenCard = "Beef";
                break;
            case R.id.dairy_card:
                chosenCard = "Dairy";
                break;
            case R.id.fish_card:
                chosenCard = "Fish";
                break;
            case R.id.vegan_card:
                chosenCard = "Vegan";
                break;
            case R.id.cocktails_card:
                chosenCard = "Cocktails";
                break;
            case R.id.desserts_card:
                chosenCard = "Desserts";
                break;
        }

        //after we found out which category chosen, we send the information to RecyclerViewActivity class
        Intent intent = new Intent(getActivity(), RecyclerViewActivity.class);
        intent.putExtra("categoryNameKey", chosenCard);
        startActivity(intent);
    }
}