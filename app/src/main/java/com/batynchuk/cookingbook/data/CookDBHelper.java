package com.batynchuk.cookingbook.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Батинчук on 04.10.2016.
 */

public class CookDBHelper extends SQLiteAssetHelper {

    private static final String DB_NAME = "cookbook.db";
    private static final int DB_VERSION = 1;

    public CookDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public Cursor getDataMain() {
        SQLiteDatabase database = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String[] sqlSelect = {"0 _id", "category_id", "name", "intro", "instruction", "image",
                "link", "time", "servings", "calories", "favorite"};

//        String[] sqlSelect = {"*"};
        String sqlTable = "recipes";

        queryBuilder.setTables(sqlTable);
        Cursor cursor = queryBuilder.query(
                database,
                sqlSelect,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        return cursor;
    }

//    public void goulashChange(){
//        SQLiteDatabase database = getWritableDatabase();
//        ContentValues contentValues =  new ContentValues();
//        contentValues.put("image",
//                "http://www.thesoupspoon.com/wp-content/uploads/2015/02/TSS_Web_Brands_Soup_BeefGoulash.jpg");
//        database.update(
//                "recipes",
//                contentValues,
//                "name =? ",
//                new String[]{"Goulash Soup"});
//    }
}
