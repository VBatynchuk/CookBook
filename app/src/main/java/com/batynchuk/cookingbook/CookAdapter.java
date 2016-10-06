package com.batynchuk.cookingbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.batynchuk.cookingbook.utils.SquareImageView;
import com.batynchuk.cookingbook.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Батинчук on 04.10.2016.
 */

public class CookAdapter extends RecyclerView.Adapter<CookAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public SquareImageView mIconDish;
        public TextView mDishName;
        public TextView mRecipeOwner;
       // public ViewHolderClick mViewHolderClick;
        public LinearLayout mLinearLayout;

        public ViewHolder(View view) {
            super(view);
           // mViewHolderClick = click;
            mIconDish = (SquareImageView) view.findViewById(R.id.icon_dish);
            mDishName = (TextView) view.findViewById(R.id.dish_name);
            mRecipeOwner = (TextView) view.findViewById(R.id.recipe_owner);
            mLinearLayout = (LinearLayout) view.findViewById(R.id.holder_layout);
           // view.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View viewClick){
//            mViewHolderClick.onItem(viewClick);
//        }
//
//        public interface ViewHolderClick{
//            void onItem(View view);
//        }
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

//        ViewHolder viewHolder = new ViewHolder(view, new ViewHolder.ViewHolderClick() {
//            @Override
//            public void onItem(View view) {
//                Intent intent = new Intent(mContext, RecipeDetail.class);
//                intent.putExtra("recipe", (android.os.Parcelable) mRecipes.get())
//                mContext.startActivity(intent);
//            }
//        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mDishName.setText(mRecipes.get(position).getName());
        String url = mRecipes.get(position).getImage();
        Picasso.with(mContext).load(url).fit().centerCrop().into(holder.mIconDish);
        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecipeDetail.class);
                intent.putExtra("recipe", holder.getAdapterPosition());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }


}
