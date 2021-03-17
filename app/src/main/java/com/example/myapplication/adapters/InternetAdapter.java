package com.example.myapplication.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Internet;

import java.util.ArrayList;

public class InternetAdapter extends RecyclerView.Adapter<InternetAdapter.InternetViewHolder>
{
    private ArrayList<Internet> internetArrayList;

    //constructor with the arrayList of internets attribute
    public InternetAdapter(ArrayList<Internet> internetArrayList)
    {
        this.internetArrayList = internetArrayList;
    }

    @NonNull
    @Override
    public InternetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //Connecting the viewHolder to the proper layout (single_row_internet layout)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_internet, parent, false);
        return new InternetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InternetViewHolder holder, int position)
    {
        //sending internet's information to the holder, so it will appear in the single row view
        holder.linkName.setText(internetArrayList.get(position).getName());
        holder.linkDescription.setText(internetArrayList.get(position).getDescription());
    }

    @Override
    public int getItemCount()
    {
        //returning the size of the arrayList
        return internetArrayList.size();
    }

    //creating class InternetViewHolder, for displaying the internets in the recycler view
    public class InternetViewHolder extends RecyclerView.ViewHolder
    {
        //each single row will contain the link name and link description
        private TextView linkName, linkDescription;

        public InternetViewHolder(@NonNull View itemView)
        {
            super(itemView);

            //setting on click which will open the link the user input
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(internetArrayList.get(getAdapterPosition()).getUrl()));
                    v.getContext().startActivity(intent);
                }
            });

            linkName = itemView.findViewById(R.id.linkNameRow);
            linkDescription = itemView.findViewById(R.id.linkDescriptionRow);
        }
    }
}
