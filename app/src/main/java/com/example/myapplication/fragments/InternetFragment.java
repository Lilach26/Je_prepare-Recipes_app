package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activities.RecyclerViewActivity;
import com.example.myapplication.adapters.InternetAdapter;
import com.example.myapplication.model.Internet;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InternetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InternetFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText linkName, linkDescription, linkInput;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Button addLinkButton;
    private ArrayList<Internet> internetArrayList;
    private Map<String, Object> internetMap;
    private RecyclerView internetRecyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InternetFragment() {
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
    public static InternetFragment newInstance(String param1, String param2) {
        InternetFragment fragment = new InternetFragment();
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
        View view = inflater.inflate(R.layout.fragment_internet, container, false);
        internetMap = new HashMap<>();
        //read data base from the fire-store
        readFromDB();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        internetArrayList = new ArrayList<>();
        internetRecyclerView = view.findViewById(R.id.linkRecyclerView);
        internetRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        linkName = view.findViewById(R.id.linkNameText);
        linkDescription = view.findViewById(R.id.linkDescriptionText);
        linkInput = view.findViewById(R.id.linkInputText);
        addLinkButton = view.findViewById(R.id.addLinkButton);

        //set click listener for the add link button
        addLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //check if all fields are filled
                if(!linkName.getText().toString().equals("") && !linkDescription.getText().toString().equals("") && !linkInput.getText().toString().equals(""))
                {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String uid = user.getUid();

                    //create new object of Internet, and save it into the data base
                    Internet obj = new Internet(linkName.getText().toString(), linkDescription.getText().toString(), linkInput.getText().toString());
                    internetArrayList.add(obj);
                    internetMap.put(linkName.getText().toString(), obj);
                    db.collection("Users").document(uid).collection("Internet Links").document("Links").set(internetMap);
                    //Notifying adapter for the new link that added
                    internetRecyclerView.setAdapter(new InternetAdapter(internetArrayList));

                    //set the input fields to empty strings
                    linkName.setText("");
                    linkDescription.setText("");
                    linkInput.setText("");
                }

                else
                {
                    Toast.makeText(getActivity(), "Fill all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //function that deletes item from internet recycler view, and update data inside fire-store db, onMove irrelevant for us
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();

                //saving the object to remove inside String object, and remove it from the internet's HashMap
                String linkForDelete = internetArrayList.get(viewHolder.getAdapterPosition()).getName();
                internetMap.remove(linkForDelete);

                //setting the new Hash map to the data-base, and make toast message if it succeed
                db.collection("Users").document(uid).collection("Internet Links").document("Links")
                        .set(internetMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Link deleted successfully!", Toast.LENGTH_SHORT).show();
                    }
                });
                //updating the recycler view list accordingly to the delete
                internetArrayList = convertHashToArray(internetMap);
                internetRecyclerView.setAdapter(new InternetAdapter(internetArrayList));
            }
        }).attachToRecyclerView(internetRecyclerView);

        return view;
    }

    //This function gets all fields of a specific document "Links", to read the data of each Internet's object inside it
    public void readFromDB()
    {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        //getting all fields of the document "Links"
        db.collection("Users").document(uid).collection("Internet Links").document("Links")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                //getting data, convert each data to the arrayList, and notify the recycler view adapter accordingly
                internetMap = documentSnapshot.getData();
                internetArrayList = convertHashToArray(internetMap);
                internetRecyclerView.setAdapter(new InternetAdapter(internetArrayList));
            }
        });
    }

    //This function will get a Map and return the same object, converted to arrayList object
    public ArrayList<Internet> convertHashToArray(Map<String, Object> map)
    {
        ArrayList<Internet> temp = new ArrayList<>();
        //iterating over the map values
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            //getting information from each object inside the map, convert it, and add it to the arrayList "temp"
            Map<String, Object> value = (Map<String, Object>) entry.getValue();
            String name = value.get("name").toString();
            String description = value.get("description").toString();
            String url = value.get("url").toString();
            Internet internet = new Internet(name, description, url);
            temp.add(internet);
        }
        return temp;
    }
}