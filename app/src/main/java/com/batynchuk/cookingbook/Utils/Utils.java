package com.batynchuk.cookingbook.Utils;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.batynchuk.cookingbook.model.Recipe;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Батинчук on 05.10.2016.
 */

public class Utils {

    public static ArrayList<Recipe> getArrayListData(Cursor cursor) {
        ArrayList<Recipe> arrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe();
                recipe.setFavourite(cursor.getInt(cursor.getColumnIndex("favorite")));
                recipe.setCategoryId(cursor.getInt(cursor.getColumnIndex("category_id")));
                recipe.setTime(cursor.getInt(cursor.getColumnIndex("time")));
                recipe.setServings(cursor.getInt(cursor.getColumnIndex("servings")));
                recipe.setCalories(cursor.getInt(cursor.getColumnIndex("calories")));
                recipe.setIntro(cursor.getString(cursor.getColumnIndex("intro")));
                recipe.setInstruction(cursor.getString(cursor.getColumnIndex("instruction")));
                recipe.setImage(cursor.getString(cursor.getColumnIndex("image")));
                recipe.setLink(cursor.getString(cursor.getColumnIndex("link")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));

                arrayList.add(recipe);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }
//
//    public static ArrayList<Bitmap> getImageMain(Cursor cursor) {
//
//    }
}
