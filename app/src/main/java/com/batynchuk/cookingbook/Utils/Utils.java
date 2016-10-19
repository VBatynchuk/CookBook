package com.batynchuk.cookingbook.utils;

import android.database.Cursor;

import com.batynchuk.cookingbook.model.Recipe;

import java.util.ArrayList;

/**
 * Created by Батинчук on 05.10.2016.
 */

public class Utils {

    public static ArrayList<Recipe> getArrayListData(Cursor cursor) {
        ArrayList<Recipe> arrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            try {
                do {
                    Recipe recipe = new Recipe();
                    recipe.setFavourite(cursor.getInt(cursor.getColumnIndex("favorite")));
                    recipe.setCategoryId(cursor.getInt(cursor.getColumnIndex("category_id")));
                    recipe.setTime(cursor.getInt(cursor.getColumnIndex("time")));
                    recipe.setServings(cursor.getString(cursor.getColumnIndex("servings")));
                    recipe.setCalories(cursor.getInt(cursor.getColumnIndex("calories")));
                    recipe.setIntro(cursor.getString(cursor.getColumnIndex("intro")));
                    recipe.setInstruction(cursor.getString(cursor.getColumnIndex("instruction")));
                    recipe.setImage(cursor.getString(cursor.getColumnIndex("image")));
                    recipe.setLink(cursor.getString(cursor.getColumnIndex("link")));
                    recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                    recipe.setAuthor("Volodymyr Batynchuk");

                    arrayList.add(recipe);
                }
                while (cursor.moveToNext());
            } finally {
                if (!cursor.isClosed())
                    cursor.close();
            }
        }
        return arrayList;
    }

    public static ArrayList<String> getIngredientsList(Cursor cursor) {
        ArrayList<String> arrayList = new ArrayList<>();
        boolean cursorBool = cursor.moveToFirst();
        if (cursorBool) {
            try {
                do {
                    arrayList.add(cursor.getString(cursor.getColumnIndex("quantity")) + " "
                            + cursor.getString(cursor.getColumnIndex("unit")) + " "
                            + cursor.getString(cursor.getColumnIndex("name")));

                } while (cursor.moveToNext());
            } finally {
                if (!cursor.isClosed())
                    cursor.close();
            }

        }
        return arrayList;
    }

}
