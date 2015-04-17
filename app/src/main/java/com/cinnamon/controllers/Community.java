package com.cinnamon.controllers;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.cinnamon.R;
import com.cinnamon.adapters.CommunityAdapters;
import com.cinnamon.lib.ApiResponse;
import com.cinnamon.models.Meal;


public class Community extends ListActivity {
    private static final String TAG = "CommunityActivity";

    private ListView        mLstMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_fragment);

        setComponents();
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
