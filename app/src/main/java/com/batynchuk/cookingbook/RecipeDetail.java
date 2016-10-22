package com.batynchuk.cookingbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.batynchuk.cookingbook.data.CookDBHelper;
import com.batynchuk.cookingbook.model.Recipe;

import com.batynchuk.cookingbook.utils.AppBarStateChangeListener;
import com.batynchuk.cookingbook.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;


public class RecipeDetail extends AppCompatActivity {

    public class RecipeViewHolder {
        public TextView mServings;
        public TextView mTime;
        public TextView mIngredientsCount;
        public TextView mAllToList;
        public TextView mAddedAnimationText;
        public ImageView mDishIcon;
        public ImageView mAddAllIcon;
        public AppBarLayout mAppBarLayout;
        public Toolbar mToolbar;
        public CollapsingToolbarLayout collapsingToolbarLayout;
        public LinearLayout mIngredientsLinLayout;
        public LinearLayout mIngredientsTop;
        public FloatingActionButton mFloatingActionButton;

        public RecipeViewHolder() {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
            mDishIcon = (ImageView) findViewById(R.id.icon_detail);
            mTime = (TextView) findViewById(R.id.time);
            mIngredientsCount = (TextView) findViewById(R.id.ingredients_count);
            mAddAllIcon = (ImageView) findViewById(R.id.add_remove_all_ingredients);
            mServings = (TextView) findViewById(R.id.servings_count);
            mIngredientsLinLayout = (LinearLayout) findViewById(R.id.ingredients_layout);
            mAllToList = (TextView) findViewById(R.id.all_to_list_text_view);
            mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
            mAddedAnimationText = (TextView) findViewById(R.id.items_added);
            mIngredientsLinLayout = (LinearLayout) findViewById(R.id.ingredients_layout);
            mIngredientsTop = (LinearLayout) findViewById(R.id.ingredients_top);
            mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        }
    }

    private boolean mAllSelected;
    private int mPosition;

    public ImageView[] mAddIngredientIcon;
    public ImageView[] mRemoveIngredientIcon;

    private View detailIngredientView;
    private RecipeViewHolder mRecipeViewHolder;
    private ShareActionProvider mShareActionProvider;
    private ArrayList<Recipe> mRecipes;
    private ArrayList<String> mIngredients;
    private Calendar mCalendarStart;
    private Calendar mCalendarEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mCalendarStart = Calendar.getInstance();

        CookDBHelper cookDBHelper = new CookDBHelper(this);
        mPosition = getIntent().getIntExtra("recipe", 0);
        mRecipes = Utils.getArrayListData(cookDBHelper.getDataMain());
        mIngredients = Utils.getIngredientsList(cookDBHelper
                .getIngredients(mPosition + 1));

        mRecipeViewHolder = new RecipeViewHolder();
        initView();

        mCalendarEnd = Calendar.getInstance();

        long time = mCalendarEnd.getTimeInMillis() - mCalendarStart.getTimeInMillis();
        Toast toast = Toast.makeText(this, "time: " + time, Toast.LENGTH_SHORT);
        toast.show();

    }

    private void initView() {

        setSupportActionBar(mRecipeViewHolder.mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecipeViewHolder.mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRecipeViewHolder.mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mRecipeViewHolder.mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.COLLAPSED) {
                    mRecipeViewHolder.mFloatingActionButton.setVisibility(View.VISIBLE);
                } else {
                    mRecipeViewHolder.mFloatingActionButton.setVisibility(View.GONE);
                }
            }
        });

        mRecipeViewHolder.collapsingToolbarLayout.setTitle(mRecipes.get(mPosition).getName());
        mRecipeViewHolder.collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));

        String url = mRecipes.get(mPosition).getImage();
        Picasso.with(this).load(url).fit().centerCrop().into(mRecipeViewHolder.mDishIcon);

        mRecipeViewHolder.mTime.setText(String.format("%s %s", mRecipes.get(mPosition).getTime(), "minutes"));

        mRecipeViewHolder.mIngredientsCount.setText(String.format("%s %s", mIngredients.size(), "ingredients"));

        mAllSelected = false;

        mRecipeViewHolder.mAddAllIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAllSelected = !mAllSelected;
                if (mAllSelected) {
                    addedClickedAnimation(getResources().getString(R.string.items_added_text));
                    mRecipeViewHolder.mAllToList.setText(getResources().getString(R.string.all_from_list));
                } else {
                    addedClickedAnimation(getResources().getString(R.string.items_removed_text));
                    mRecipeViewHolder.mAllToList.setText(getResources().getString(R.string.all_to_list));
                }
                initIngredient();
            }
        });

        mRecipeViewHolder.mServings.setText(mRecipes.get(mPosition).getServings());

        initIngredient();

//        int height = mRecipeViewHolder.mIngredientsTop.getLayoutParams().height;
//
//        mRecipeViewHolder.mAddedAnimationText.setLayoutParams(
//                new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        height));

    }

    private void addedClickedAnimation(String text) {

        mRecipeViewHolder.mAddedAnimationText.setText(text);
        mRecipeViewHolder.mIngredientsTop.setVisibility(View.GONE);
        mRecipeViewHolder.mIngredientsTop.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecipeViewHolder.mIngredientsTop.setVisibility(View.VISIBLE);
            }
        }, 3000);

    }

    private void initIngredient() {
        boolean color = true;

        mRecipeViewHolder.mIngredientsLinLayout.removeAllViews();
        // int iconResource = addedIngredient(mAllSelected);

        mAddIngredientIcon = new ImageView[mIngredients.size()];
        mRemoveIngredientIcon = new ImageView[mIngredients.size()];
        for (int i = 0; i < mIngredients.size(); i++) {

            final int index = i;

            LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            detailIngredientView = layoutInflater.inflate(R.layout.detail_ingredient_item, null);

            mAddIngredientIcon[i] = (ImageView) detailIngredientView.findViewById(R.id.add_ingredient);
            mRemoveIngredientIcon[i] = (ImageView) detailIngredientView.findViewById(R.id.remove_ingredient);

            if (color) {
                detailIngredientView.setBackground(getResources().getDrawable(R.drawable.ingredient_light_back));
            } else {
                detailIngredientView.setBackgroundColor(getResources().getColor(R.color.ingredient_dark));
            }

            if (mAllSelected) {
                mRemoveIngredientIcon[i].setVisibility(View.VISIBLE);
            }

            TextView textView = (TextView) detailIngredientView.findViewById(R.id.ingredients_list_item_text);
            textView.setText(mIngredients.get(i));

            mRecipeViewHolder.mIngredientsLinLayout.addView(detailIngredientView, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            mAddIngredientIcon[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v == mAddIngredientIcon[index]) {
                        mRemoveIngredientIcon[index].setVisibility(View.VISIBLE);
                    }
                }
            });

            mRemoveIngredientIcon[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v == mRemoveIngredientIcon[index]) {
                        mRemoveIngredientIcon[index].setVisibility(View.GONE);
                    }
                }
            });

            color = !color;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.menu_recipe_detail, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem shareItem = menu.findItem(R.id.action_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        setShareIntent(createShareIntent());

        // Return true to display menu
        return true;
    }

    public Intent createShareIntent() {
        // populate the share intent with data
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mRecipes.get(mPosition).getName());
        return shareIntent;
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

}
