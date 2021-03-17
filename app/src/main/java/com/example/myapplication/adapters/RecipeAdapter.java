package com.example.myapplication.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activities.RecipeView;
import com.example.myapplication.model.Recipe;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>
{
    private ArrayList<Recipe> recipesArray;

    //constructor with the arrayList of recipes attribute
    public RecipeAdapter(ArrayList<Recipe> recipesArray)
    {
        this.recipesArray = recipesArray;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //Connecting the viewHolder to the proper layout (single_row_recipe layout)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position)
    {
        //sending recipe's information to the holder, so it will appear in the single row view
        holder.recipeName.setText(recipesArray.get(position).getRecipeName());
        holder.recipeDescription.setText(recipesArray.get(position).getDescription());
    }

    @Override
    public int getItemCount()
    {
        //returning the size of the arrayList
        return recipesArray.size();
    }

    //creating class RecipeViewHolder, for displaying the recipes in the recycler view
    public class RecipeViewHolder extends RecyclerView.ViewHolder
    {
        //each single row will contain the recipe name and recipe description
        TextView recipeName, recipeDescription;

        public RecipeViewHolder(@NonNull View itemView)
        {
            super(itemView);

            //setting on click which will open the recipe's view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //Sending recipe's information the the RecipeView activity to show the recipe's details (using intent)
                    Intent intent = new Intent(v.getContext(), RecipeView.class);
                    intent.putExtra("nameKey", recipesArray.get(getAdapterPosition()).getRecipeName());
                    intent.putExtra("descriptionKey", recipesArray.get(getAdapterPosition()).getDescription());
                    intent.putExtra("stepsKey", recipesArray.get(getAdapterPosition()).getSteps());
                    intent.putExtra("categoryKey", recipesArray.get(getAdapterPosition()).getCategory());

                    String ingredients = "";
                    for (String iterate : recipesArray.get(getAdapterPosition()).getIngredients())
                    {
                        ingredients += iterate + "\n";
                    }

                    intent.putExtra("ingredientsKey", ingredients);
                    v.getContext().startActivity(intent);
                }
            });

            recipeName = itemView.findViewById(R.id.recipeNameRow);
            recipeDescription = itemView.findViewById(R.id.recipeDescriptionRow);
        }
    }
}
