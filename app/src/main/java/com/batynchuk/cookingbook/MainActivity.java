package com.batynchuk.cookingbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.batynchuk.cookingbook.adapters.CookAdapter;
import com.batynchuk.cookingbook.adapters.IRecipeOnClickListener;
import com.batynchuk.cookingbook.utils.Utils;
import com.batynchuk.cookingbook.data.CookDBHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IRecipeOnClickListener {

    private CookDBHelper mCookDBHelper;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mCookDBHelper = new CookDBHelper(this);

        initView();
    }

    private void initView() {

        setSupportActionBar(toolbar);

        CookAdapter cookAdapter = new CookAdapter(this,
                Utils.getArrayListData(mCookDBHelper.getDataMain()), // get list with recipe info
                this);

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cookAdapter);
        recyclerView.setLayoutManager(layoutManager);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_recipes) {
            // Handle the camera action
        } else if (id == R.id.nav_favourite) {

        } else if (id == R.id.nav_shop_list) {

        } else if (id == R.id.nav_about) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRecipeItemClick(View imageAnimView, int position) {

        Intent intent = new Intent(this, RecipeDetail.class);
        intent.putExtra("recipe", position);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, imageAnimView, "dish_icon_transition");
        this.startActivity(intent, options.toBundle());
    }
}
