package com.example.sofraapp.app.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sofraapp.R;
import com.example.sofraapp.app.helper.SaveData;
import com.example.sofraapp.app.ui.fragment.ReviewsFragment;
import com.example.sofraapp.app.ui.fragment.InformationStoreFragment;
import com.example.sofraapp.app.ui.fragment.ListFoodFragment;

import static com.example.sofraapp.app.helper.HelperMethod.GET_DATA;

public class AdapterDetailsFood extends FragmentPagerAdapter {
    private Context mContext;
    Bundle bundle;
    SaveData saveData;
    public AdapterDetailsFood(Context mContext, FragmentManager fm, SaveData saveData) {
        super(fm);
        this.mContext = mContext;
        this.saveData = saveData;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            ListFoodFragment listFoodFragment = new ListFoodFragment();
            bundle = new Bundle();
            bundle.putParcelable(GET_DATA,saveData);
            listFoodFragment.setArguments(bundle);
            return listFoodFragment;
        }else if (position == 1){
            ReviewsFragment reviewsFragment = new ReviewsFragment();
            bundle = new Bundle();
            bundle.putParcelable(GET_DATA,saveData);
            reviewsFragment.setArguments(bundle);
            return reviewsFragment;
        }else {
            InformationStoreFragment informationStoreFragment =  new InformationStoreFragment();
            bundle = new Bundle();
            bundle.putParcelable(GET_DATA,saveData);
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













