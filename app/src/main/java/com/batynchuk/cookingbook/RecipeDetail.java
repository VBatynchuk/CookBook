package com.batynchuk.cookingbook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.batynchuk.cookingbook.data.CookDBHelper;
import com.batynchuk.cookingbook.model.Recipe;
import com.batynchuk.cookingbook.utils.Utils;

import java.util.ArrayList;

public class RecipeDetail extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // next code is only for test purposes 
        textView = (TextView) findViewById(R.id.instruction);

        int position = getIntent().getIntExtra("recipe", 0);
        ArrayList<Recipe> arrayListData = Utils.getArrayListData(new CookDBHelper(this).getDataMain());

        textView.setText(arrayListData.get(position).getInstruction());
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


    }
}
