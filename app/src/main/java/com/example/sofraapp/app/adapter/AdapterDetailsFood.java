package com.example.sofraapp.app.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sofraapp.R;
import com.example.sofraapp.app.helper.SaveData;
import com.example.sofraapp.app.ui.fragment.restaurant.ReviewsFragment;
import com.example.sofraapp.app.ui.fragment.restaurant.InformationStoreFragment;
import com.example.sofraapp.app.ui.fragment.restaurant.ListFoodFragment;

import static com.example.sofraapp.app.helper.HelperMethod.GET_DATA;
import static com.example.sofraapp.app.ui.fragment.general.restaurant.OrderFoodFragment.DETAILS_RESTUARANT;

public class AdapterDetailsFood extends FragmentPagerAdapter {
    private Context mContext;
    SaveData saveData;
    public AdapterDetailsFood(Context mContext, FragmentManager fm, SaveData saveData) {
        super(fm);
        this.mContext = mContext;
        this.saveData = saveData;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            Bundle bundle = new Bundle();
            bundle.putParcelable(GET_DATA, saveData);
            ListFoodFragment listFoodFragment = new ListFoodFragment();
            listFoodFragment.setArguments(bundle);
            return listFoodFragment;
        }else if (position == 1){
            Bundle bundle = new Bundle();
            bundle.putParcelable(GET_DATA, saveData);
            ReviewsFragment reviewsFragment = new ReviewsFragment();
            reviewsFragment.setArguments(bundle);
            return reviewsFragment;
        }else {
            Bundle bundle = new Bundle();
            bundle.putParcelable(GET_DATA, saveData);
            InformationStoreFragment informationStoreFragment =  new InformationStoreFragment();
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













