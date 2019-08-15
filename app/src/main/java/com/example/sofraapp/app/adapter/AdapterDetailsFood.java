package com.example.sofraapp.app.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.general.restaurants.Data2Restaurants;
import com.example.sofraapp.app.ui.fragment.restaurant.ReviewsFragment;
import com.example.sofraapp.app.ui.fragment.restaurant.InformationStoreFragment;
import com.example.sofraapp.app.ui.fragment.restaurant.ListFoodFragment;
import com.google.gson.Gson;

public class AdapterDetailsFood extends FragmentPagerAdapter {
    private Context mContext;
    private Data2Restaurants data2Restaurants;

    public AdapterDetailsFood(Context mContext, FragmentManager fm,Data2Restaurants dataRrestaurantDetails) {
        super(fm);
        this.mContext = mContext;
        this.data2Restaurants = dataRrestaurantDetails;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            Bundle bundle = new Bundle();
            bundle.putString("dev",new Gson().toJson(data2Restaurants));
            ListFoodFragment listFoodFragment = new ListFoodFragment();
            listFoodFragment.setArguments(bundle);
            return listFoodFragment;
        }else if (position == 1){
            Bundle bundle = new Bundle();
            bundle.putString("dev",new Gson().toJson(data2Restaurants));
            ReviewsFragment reviewsFragment = new ReviewsFragment();
            reviewsFragment.setArguments(bundle);
            return reviewsFragment;
        }else {
            Bundle bundle = new Bundle();
            InformationStoreFragment informationStoreFragment =  new InformationStoreFragment();
            bundle.putString("dev",new Gson().toJson(data2Restaurants));
            informationStoreFragment.setArguments(bundle);
            return informationStoreFragment;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0 ){
            return mContext.getString(R.string.list_food);
        }else if (position == 1){
            return mContext.getString(R.string.comments_food);
        }else {
            return mContext.getString(R.string.information_store);

        }
    }
}













