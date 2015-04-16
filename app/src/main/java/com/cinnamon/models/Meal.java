package com.cinnamon.models;

import android.content.Context;
import android.util.Log;

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
    private static final String TAG = "MealClass";

    private static interface MealImageDimensions {
        int LENGTH      = 2;

        int ORIGINAL    = 0;
        int SMALL       = 1;
    }

    // Model properties
    private String      mDescription;
    private Date        mCreated_at;
    private String[]    mImagesUrl                  = new String[MealImageDimensions.LENGTH];

    // Model values keys
    private static final String DESCRIPTION_KEY     = "title";
    private static final String CREATED_AT_KEY      = "created_at";

    private static final String[] IMAGES_URL_KEY    = new String[]{"photo_original_url", "photo_thumb_url"};

    // CONSTRUCTORS
    public Meal(JSONObject jsonMeal) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

        try {
            mDescription    = jsonMeal.getString(DESCRIPTION_KEY);

            mCreated_at     = formatter.parse(jsonMeal.getString(CREATED_AT_KEY));

            mImagesUrl      = new String[]{
                jsonMeal.getString(IMAGES_URL_KEY[MealImageDimensions.ORIGINAL]),
                jsonMeal.getString(IMAGES_URL_KEY[MealImageDimensions.SMALL])
            };
        } catch (JSONException | ParseException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    // ISTANCE METHODS
    public static void index(Context context, final ApiResponse.Listern responseListener){
        String apiUrl = "https://murmuring-dusk-8873.herokuapp.com/meal_records.json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject jsonObject) {

                Meal[] meals;

                JSONArray jsonArray = (new JSONArray()).put(jsonObject);

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
