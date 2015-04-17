package com.cinnamon.models;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.Response;

import com.android.volley.toolbox.Volley;
import com.cinnamon.lib.ApiResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Meal {
    private static final String TAG = "MealClass";

    public static interface MealImageDimensions { int LENGTH = 2; int ORIGINAL = 0; int SMALL = 1; }

    public static interface UserImageDimensions { int LENGTH = 3; int NANO = 0; int MICRO = 1; int THUMBNAIL = 2; }

    // Model properties
    private String      mDescription;
    private Date        mCreated_at;
    private String[]    mImagesUrl                  = new String[MealImageDimensions.LENGTH];
    private String[]    mUserImagesUrl              = new String[UserImageDimensions.LENGTH];

    // Model values keys
    private static final String DESCRIPTION_KEY         = "title";
    private static final String CREATED_AT_KEY          = "created_at";

    private static final String[] USER_IMAGES_URL_KEY   = new String[]{"profile_picture_nano_url", "profile_picture_micro_url", "profile_picture_thumbnail_url"};
    private static final String[] MEAL_IMAGES_URL_KEY   = new String[]{"photo_original_url", "photo_thumb_url"};

    // CONSTRUCTORS
    public Meal(JSONObject jsonMeal) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        try {
            mDescription    = jsonMeal.getString(DESCRIPTION_KEY);

            mCreated_at     = formatter.parse(jsonMeal.getString(CREATED_AT_KEY));
//            mCreated_at     = new Date();

            mImagesUrl      = new String[]{
                jsonMeal.getString(MEAL_IMAGES_URL_KEY[MealImageDimensions.ORIGINAL]),
                jsonMeal.getString(MEAL_IMAGES_URL_KEY[MealImageDimensions.SMALL])
            };

            mUserImagesUrl  = new String[]{
                jsonMeal.getJSONObject("user").getString(USER_IMAGES_URL_KEY[UserImageDimensions.NANO]),
                jsonMeal.getJSONObject("user").getString(USER_IMAGES_URL_KEY[UserImageDimensions.MICRO]),
                jsonMeal.getJSONObject("user").getString(USER_IMAGES_URL_KEY[UserImageDimensions.THUMBNAIL])
            };

        } catch (JSONException | ParseException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    // ISTANCE METHODS
    public static void index(Context context, final ApiResponse.Listener<Meal[]> responseListener){
        String apiUrl = "https://murmuring-dusk-8873.herokuapp.com/meal_records.json";

        JsonArrayRequest request = new JsonArrayRequest(apiUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Meal[] meals;

                try{
                    meals = new Meal[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length() && i < 50; i++) {
                        JSONObject jsonMeal = jsonArray.getJSONObject(i);

                        meals[i] = new Meal(jsonMeal);
                    }

                    responseListener.onSuccess(meals);

                }catch (JSONException e){
                    e.printStackTrace();

                    responseListener.onErrorResponse(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);

        queue.add(request);

        queue.start();
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

    public String getUserImageUrl(int dimension){
        if (dimension >= UserImageDimensions.LENGTH);
            dimension = UserImageDimensions.THUMBNAIL;

        return mUserImagesUrl[dimension];
    }
}
