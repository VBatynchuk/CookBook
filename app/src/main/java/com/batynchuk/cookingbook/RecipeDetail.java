package com.batynchuk.cookingbook;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.batynchuk.cookingbook.data.CookDBHelper;
import com.batynchuk.cookingbook.model.Recipe;
import com.batynchuk.cookingbook.utils.AppBarStateChangeListener;
import com.batynchuk.cookingbook.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RecipeDetail extends AppCompatActivity {

    private boolean mAllSelected;

    @BindView(R.id.all_to_list_text_view)
    TextView mAllToList;
    @BindView(R.id.items_added)
    TextView mAddedAnimationText;
    @BindView(R.id.fab)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.ingredients_layout)
    LinearLayout mIngredientsLinLayout;
    @BindView(R.id.ingredients_top)
    LinearLayout mIngredientsTop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.icon_detail)
    ImageView mDishIcon;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.ingredients_count)
    TextView ingredientsCount;
    @BindView(R.id.servings_count)
    TextView servings;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;


    private ImageView[] mAddIngredientIcon;
    private ImageView[] mRemoveIngredientIcon;

    private Recipe mRecipe;
    private ArrayList<String> mIngredients;

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        ButterKnife.bind(this);

        Calendar timeTestStart = Calendar.getInstance();

        initData();
        initView();
        initIngredient();

        Calendar timeTestEnd = Calendar.getInstance();

        long time = timeTestEnd.getTimeInMillis() - timeTestStart.getTimeInMillis();
        Toast toast = Toast.makeText(this, "time: " + time, Toast.LENGTH_SHORT);
        toast.show();

    }

    private void initData() {

        CookDBHelper cookDBHelper = new CookDBHelper(this);
        int position = getIntent().getIntExtra("recipe", 0);

        ArrayList<Recipe> mRecipes = Utils.getArrayListData(cookDBHelper.getDataMain());
        mRecipe = mRecipes.get(position);
        mIngredients = Utils.getIngredientsList(cookDBHelper
                .getIngredients(position + 1));
    }

    private void initView() {
//        ImageView addAllIcon = (ImageView) findViewById(R.id.add_remove_all_ingredients);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.COLLAPSED) {
                    mFloatingActionButton.setVisibility(View.VISIBLE);
                } else if (state == State.EXPANDED || state == State.IDLE) {
                    mFloatingActionButton.setVisibility(View.GONE);
                }
            }
        });

        mCollapsingToolbarLayout.setTitle(mRecipe.getName());
        mCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));

        String url = mRecipe.getImage();
        Picasso.with(this).load(url).fit().centerCrop().into(mDishIcon);

        mTime.setText(String.format("%s %s", mRecipe.getTime(), "minutes"));

        ingredientsCount.setText(String.format("%s %s", mIngredients.size(), "ingredients"));

        mAllSelected = false;

        servings.setText(mRecipe.getServings());


//        int height = mIngredientsTop.getLayoutParams().height;
//
//        mAddedAnimationText.setLayoutParams(
//                new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        height));

    }

    @BindDrawable(R.drawable.ingredient_light_back)
    Drawable ingredientLightBack;
    @BindColor(R.color.ingredient_dark)
    int ingredientDark;

    private void initIngredient() {

        mIngredientsLinLayout.removeAllViews();

        mAddIngredientIcon = new ImageView[mIngredients.size()];
        mRemoveIngredientIcon = new ImageView[mIngredients.size()];

        View detailIngredientView;

        for (int i = 0; i < mIngredients.size(); i++) {

            final int index = i;

            LayoutInflater layoutInflater =
                    (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            detailIngredientView = layoutInflater.inflate(R.layout.detail_ingredient_item, null);

            mAddIngredientIcon[i] =
                    (ImageView) detailIngredientView.findViewById(R.id.add_ingredient);
            mRemoveIngredientIcon[i] =
                    (ImageView) detailIngredientView.findViewById(R.id.remove_ingredient);

            if (index % 2 == 0) { // change color for odd positions
                detailIngredientView.setBackground(ingredientLightBack);
            } else {
                detailIngredientView.setBackgroundColor(ingredientDark);
            }

            if (mAllSelected) {
                mRemoveIngredientIcon[i].setVisibility(View.VISIBLE);
            }

            TextView ingredientsItem =
                    (TextView) detailIngredientView.findViewById(R.id.ingredients_list_item_text);
            ingredientsItem.setText(mIngredients.get(i));

            mIngredientsLinLayout.addView(detailIngredientView, 0,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));

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

        }

    }

    private void addedClickedAnimation(String text) {

        mAddedAnimationText.setText(text);
        mIngredientsTop.animate()
                .alpha(0f)
                .setDuration(1000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mIngredientsTop.animate()
                                .alpha(1f)
                                .setDuration(1000);
//                                .setStartDelay(2000);
                    }
                });


//        mRecipeViewHolder.mIngredientsTop.setVisibility(View.GONE);
//        mRecipeViewHolder.mIngredientsTop.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mRecipeViewHolder.mIngredientsTop.setVisibility(View.VISIBLE);
//            }
//        }, 3000);

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
        shareIntent.putExtra(Intent.EXTRA_TEXT, mRecipe.getName());
        return shareIntent;
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.add_remove_all_ingredients:
//                mAllSelected = !mAllSelected;
//                if (mAllSelected) {
//                    addedClickedAnimation(getResources().getString(R.string.items_added_text));
//                    mAllToList.setText(getResources().getString(R.string.all_from_list));
//                } else {
//                    addedClickedAnimation(getResources().getString(R.string.items_removed_text));
//                    mAllToList.setText(getResources().getString(R.string.all_to_list));
//                }
//                initIngredient();
//                break;
//            case R.id.fab:
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                break;
////            case R.id.toolbar:
////                onBackPressed();
////                break;
//        }
//    }

    @BindString(R.string.items_added_text)
    String itemsAddedText;
    @BindString(R.string.items_removed_text)
    String itemsRemovedText;
    @BindString(R.string.all_from_list)
    String allFromList;
    @BindString(R.string.all_to_list)
    String allToList;

    @OnClick(R.id.add_remove_all_ingredients)
    public void ingredientClick() {
        mAllSelected = !mAllSelected;
        if (mAllSelected) {
            addedClickedAnimation(itemsAddedText);
            mAllToList.setText(allFromList);
        } else {
            addedClickedAnimation(itemsRemovedText);
            mAllToList.setText(allToList);
        }
        initIngredient();
    }
}
