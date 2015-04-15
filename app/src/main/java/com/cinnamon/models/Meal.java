package com.cinnamon.models;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Response;

import com.cinnamon.lib.ApiResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Meal {
    private static interface MealImageDimensions {
        int LENGTH      = 4;

        int ORIGINAL    = 0;
        int SMALL       = 1;
        int MEDIUM      = 2;
        int LARGE       = 3;
    }

    // Model properties
    private String      mDescription;
    private Date        mCreated_at;
    private String[]    mImagesUrl = new String[MealImageDimensions.LENGTH];

    // Model values keys
    private static final String DESCRIPTION_KEY     = "title";
    private static final String CREATED_AT_KEY      = "created_at";

    // CONSTRUCTORS
    public Meal(JSONObject jsonMeal) {

    }

    // ISTANCE METHODS
    public static void index(Context context, final ApiResponse.Listern responseListener){
        String apiUrl = "https://murmuring-dusk-8873.herokuapp.com/meal_records.json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray jsonArray) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                Meal[] meals;

                try{
                    meals = new Meal[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonMeal = jsonArray.getJSONObject(i);

                        meals[i] = new Meal(jsonMeal);
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
    }

    // GETTER METHODS
    public String getDescription() { return mDescription; }

    public Date getCreatedAt() { return mCreated_at; }

    public String getImageUrl() { return mImagesUrl[MealImageDimensions.ORIGINAL]; }

    public String getImageUrl(int dimension) {
        if (dimension >= MealImageDimensions.LENGTH);
            dimension = MealImageDimensions.ORIGINAL;

        return mImagesUrl[dimension];
    }
}
