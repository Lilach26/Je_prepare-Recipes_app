package com.example.myapplication.logic;

import java.util.HashMap;

public class Recipe
{
    private String recipeName;
    private String description;
    private HashMap<String, String> ingredients;
    private String steps;
    private String category;
    private String imageUrl;

    public Recipe(String recipeName, String description, HashMap<String, String> ingredients, String steps, String category, String imageUrl)
    {
        this.recipeName = recipeName;
        this.description = description;
        this.ingredients = new HashMap<String, String>();
        this.steps = steps;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public Recipe()
    {
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<String, String> getIngredients() {
        return ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIngredients(HashMap<String, String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
