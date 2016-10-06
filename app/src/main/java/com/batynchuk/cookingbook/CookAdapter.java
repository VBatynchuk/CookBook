package com.batynchuk.cookingbook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.batynchuk.cookingbook.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Батинчук on 04.10.2016.
 */

public class CookAdapter extends RecyclerView.Adapter<CookAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mIconDish;
        public TextView mDishName;
        public TextView mRecipeOwner;

        public ViewHolder(View view) {
            super(view);
            mIconDish = (ImageView) view.findViewById(R.id.icon_dish);
            mDishName = (TextView) view.findViewById(R.id.dish_name);
            mRecipeOwner = (TextView) view.findViewById(R.id.recipe_owner);
        }
    }

    private ArrayList<Recipe> mRecipes;

    private Context mContext;

    public CookAdapter(ArrayList<Recipe> recipe, Context context) {
        mRecipes = recipe;
        mContext = context;
    }

    @Override
    public CookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_dish, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mDishName.setText(mRecipes.get(position).getName());
        String url = mRecipes.get(position).getImage();
        Picasso.with(mContext).load(url).into(holder.mIconDish);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }


}
