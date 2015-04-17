package com.cinnamon.controllers;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.cinnamon.R;
import com.cinnamon.adapters.CommunityAdapters;
import com.cinnamon.lib.ApiResponse;
import com.cinnamon.models.Meal;

/**
 * Created by salvatore on 4/16/2015.
 */
public class CommunityFragment  extends Fragment {
    private static final String TAG = "CommunityActivity";

    private ListView    mLstMeal;
    private ProgressBar mPgbCommunityLoading;
    private View        mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.community_fragment, container, false);
        return mRootView;
    }

    public void onResume(){
        super.onResume();

        mLstMeal                = (ListView) mRootView.findViewById(R.id.list_view);
        mPgbCommunityLoading    = (ProgressBar) mRootView.findViewById(R.id.fc_feed_loading);

        mLstMeal.setVisibility(View.INVISIBLE);
        mPgbCommunityLoading.setVisibility(View.VISIBLE);

        Meal.index(mRootView.getContext(), new ApiResponse.Listener<Meal[]>() {
            @Override
            public void onSuccess(Meal[] meals) {
                CommunityAdapters adapter = new CommunityAdapters(mRootView.getContext(), meals);

                mLstMeal.setAdapter(adapter);

                mPgbCommunityLoading.setVisibility(View.INVISIBLE);
                mLstMeal.setVisibility(View.VISIBLE);
            }

            @Override
            public void onErrorResponse(Exception error) {
                Log.e(TAG, "Meal index error: " + error.getMessage());
            }
        });
    }
}