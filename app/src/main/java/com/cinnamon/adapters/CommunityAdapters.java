package com.cinnamon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinnamon.R;
import com.cinnamon.models.Meal;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class CommunityAdapters extends BaseAdapter {
    private Context mContext;
    private Meal[]  mMeals;

    public CommunityAdapters(Context context, Meal[] meals) {
        mContext = context;
        mMeals = meals;
    }

    @Override
    public int getCount() {
        return mMeals.length;
    }

    @Override
    public Object getItem(int position) {
        return mMeals[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.community_list_element, null);
            holder = new ViewHolder();

            holder.mealImage        = (ImageView) convertView.findViewById(R.id.cle_meal_image);
            holder.userImage        = (ImageView) convertView.findViewById(R.id.cle_meal_user_image);
            holder.mealDescription  = (TextView) convertView.findViewById(R.id.cle_meal_description);
            holder.mealCreatedAt    = (TextView) convertView.findViewById(R.id.cle_meal_created_at);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Meal meal = mMeals[position];

        holder.mealDescription.setText(meal.getDescription());

        holder.mealCreatedAt.setText((new SimpleDateFormat("yyyy-MM-dd")).format(meal.getCreatedAt()));


        Picasso.with(mContext).load(meal.getImageUrl()).into(holder.mealImage);

        Picasso.with(mContext).load(meal.getUserImageUrl(Meal.UserImageDimensions.NANO)).into(holder.userImage);

        return convertView;
    }

    private static class ViewHolder {
        ImageView   userImage;
        TextView    mealDescription;
        TextView    mealCreatedAt;
        ImageView   mealImage;
    }
}









