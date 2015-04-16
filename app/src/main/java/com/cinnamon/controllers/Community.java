package com.cinnamon.controllers;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.cinnamon.R;
import com.cinnamon.adapters.CommunityAdapters;
import com.cinnamon.lib.ApiResponse;
import com.cinnamon.models.Meal;

import java.util.ArrayList;


public class Community extends ListActivity {
    private static final String TAG = "CommunityActivity";

    private ListView        mLstMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        setComponents();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.community, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setComponents() {
        mLstMeal = (ListView) findViewById(android.R.id.list);

        loadMeals();
    }

    private void loadMeals() {
        Meal.index(this, new ApiResponse.Listener<Meal[]>() {
            @Override
            public void onSuccess(Meal[] meals) {
                CommunityAdapters adapter = new CommunityAdapters(Community.this, meals);

                setListAdapter(adapter);
            }

            @Override
            public void onErrorResponse(Exception error) {
                Log.e(TAG, "Meal index error: " + error.getMessage());
            }
        });
    }
}
