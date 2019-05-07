package com.example.sofraapp.app.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sofraapp.R;
import com.example.sofraapp.app.helper.SaveData;
import com.example.sofraapp.app.ui.fragment.CurrentOrderAsUSerFragment;
import com.example.sofraapp.app.ui.fragment.PreviousOrderAsUserFragment;

import static com.example.sofraapp.app.helper.HelperMethod.GET_DATA;

public class AdapterMyOrderUser extends FragmentPagerAdapter {
    private Context mContext;
    Bundle bundle;
    SaveData saveData;
    public AdapterMyOrderUser(Context mContext, FragmentManager fm, SaveData saveData) {
        super(fm);
        this.mContext = mContext;
        this.saveData = saveData;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            CurrentOrderAsUSerFragment currentOrderAsUSerFragment = new CurrentOrderAsUSerFragment();
            bundle = new Bundle();
            bundle.putParcelable(GET_DATA, saveData);
            currentOrderAsUSerFragment.setArguments(bundle);
            return currentOrderAsUSerFragment;
        } else {
            PreviousOrderAsUserFragment previousOrderAsUserFragment = new PreviousOrderAsUserFragment();
            bundle = new Bundle();
            bundle.putParcelable(GET_DATA, saveData);
            previousOrderAsUserFragment.setArguments(bundle);
            return previousOrderAsUserFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.current_order);
        } else {
            return mContext.getString(R.string.previus_order);

        }
    }
}