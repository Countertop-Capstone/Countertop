package com.capstone.countertop.services;

import com.capstone.countertop.models.Ingredient;
import com.capstone.countertop.repositories.IngredientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Help {
    public static List<Ingredient> parseIngredients(String ingredients) {
        String[] ingredientArray = ingredients.split("pBREAKq");
        for(int i=0; i<ingredientArray.length; i++) {
            ingredientArray[i] = ingredientArray[i].trim().toLowerCase();
        }

        List<Ingredient> ingredientList = new ArrayList<>();
        for(String string : ingredientArray) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(string);
            ingredientList.add(ingredient);
        }

        for(Ingredient ingredient : ingredientList) {
            System.out.println(ingredient.getName());
        }
        ingredientList.remove(ingredientList.size() -1);
        return ingredientList;
    }

    public static String[] parseInstructions(String instructions){
        String[] instructionArray = instructions.split(Pattern.quote("."));
        for (String instruction: instructionArray){
            System.out.println(instruction);
        }
        return instructionArray;

    }
}

