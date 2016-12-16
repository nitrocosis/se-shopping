package com.srgiovine.seshopping;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.srgiovine.seshopping.navigation.FilterNavigationItem;
import com.srgiovine.seshopping.navigation.NavigationAdapter;
import com.srgiovine.seshopping.navigation.NavigationDrawerToggle;
import com.srgiovine.seshopping.navigation.NavigationItemFactory;
import com.srgiovine.seshopping.navigation.SettingsNavigationItem;

import srgiovine.com.seshopping.R;

public class BrowseActivity extends Activity {

    private NavigationDrawerToggle navigationDrawerToggle;

    private final NavigationAdapter.EventListener navigationEventListener = new NavigationAdapter.EventListener() {

        @Override
        public void onFilterItemClicked(FilterNavigationItem item) {
            onFilterClicked(item);
        }

        @Override
        public void onSettingsItemClicked(SettingsNavigationItem item) {
            onSettingsClicked();
        }
    };

    private final NavigationAdapter navigationAdapter = new NavigationAdapter(navigationEventListener);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        RecyclerView navigationDrawer = (RecyclerView) findViewById(R.id.navigation_drawer);
        navigationDrawer.setLayoutManager(new LinearLayoutManager(this));
        navigationDrawer.setAdapter(navigationAdapter);

        navigationDrawerToggle = new NavigationDrawerToggle(this, drawerLayout);
        navigationAdapter.addItems(NavigationItemFactory.createNavigationItems());

        drawerLayout.addDrawerListener(navigationDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        navigationDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        navigationDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.browse_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (navigationDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (R.id.action_open_cart == item.getItemId()) {
            openCart();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onSettingsClicked() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void onFilterClicked(FilterNavigationItem item) {
        // TODO update the filtered items
    }

    private void openCart() {
        startActivity(new Intent(this, CartActivity.class));
    }
}
