package com.example.myapplication.model;

import java.util.ArrayList;

public class Recipe
{
    private String recipeName;
    private String description;
    private ArrayList<String> ingredients;
    private String steps;
    private String category;

    public Recipe() {}

    public Recipe(String recipeName, String description, ArrayList<String> ingredients, String steps, String category)
    {
        this.recipeName = recipeName;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.category = category;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public String getCategory() {
        return category;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
