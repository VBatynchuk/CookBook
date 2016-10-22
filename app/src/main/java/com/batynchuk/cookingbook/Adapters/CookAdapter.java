package com.batynchuk.cookingbook.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.batynchuk.cookingbook.R;
import com.batynchuk.cookingbook.RecipeDetail;
import com.batynchuk.cookingbook.utils.SquareImageView;
import com.batynchuk.cookingbook.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Батинчук on 04.10.2016.
 */

public class CookAdapter extends RecyclerView.Adapter<CookAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public SquareImageView mIconDish;
        public TextView mDishName;
        public TextView mRecipeOwner;
        public LinearLayout mLinearLayout;

        public ViewHolder(View view) {
            super(view);
            mIconDish = (SquareImageView) view.findViewById(R.id.icon_dish);
            mDishName = (TextView) view.findViewById(R.id.dish_name);
            mRecipeOwner = (TextView) view.findViewById(R.id.recipe_owner);
            mLinearLayout = (LinearLayout) view.findViewById(R.id.holder_layout);
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mDishName.setText(mRecipes.get(position).getName());
        String url = mRecipes.get(position).getImage();
        Picasso.with(mContext).load(url).fit().centerCrop().into(holder.mIconDish);

        String recipeAuthor = String.format("%s %s", mContext.getString(R.string.recipe_author),
                mRecipes.get(position).getAuthor());

        holder.mRecipeOwner.setText(Html.fromHtml(recipeAuthor));
        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecipeDetail.class);
                intent.putExtra("recipe", holder.getAdapterPosition());
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) mContext, holder.mIconDish, "dish_icon_transition");
                mContext.startActivity(intent, options.toBundle());
//                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }


}
