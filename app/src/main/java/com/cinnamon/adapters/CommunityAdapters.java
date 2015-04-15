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

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.community_list_element, null);
            holder = new ViewHolder();

//            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
//            holder.mealDescription = (TextView) convertView.findViewById(R.id.temperatureLabel);
//            holder.dayLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Meal meal = mMeals[position];

//        holder.iconImageView.setImageResource(day.getIconId());
        holder.mealDescription.setText(meal.getDescription());
        holder.mealCreatedAt.setText(meal.getCreatedAt().toString());

        return convertView;
    }

    private static class ViewHolder {
        ImageView userImage;
        TextView mealDescription;
        TextView mealCreatedAt;
        ImageView mealImage;
    }
}









