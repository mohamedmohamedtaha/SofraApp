package com.example.sofraapp.app.adapter;

import android.content.Context;

import com.example.sofraapp.R;
import com.example.sofraapp.app.ui.fragment.client.order.CurrentOrderAsUSerFragment;
import com.example.sofraapp.app.ui.fragment.client.order.PreviousOrderAsUserFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdapterMyOrderUser extends FragmentPagerAdapter {
    private Context mContext;

    public AdapterMyOrderUser(Context mContext, FragmentManager fm) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            CurrentOrderAsUSerFragment currentOrderAsUSerFragment = new CurrentOrderAsUSerFragment();
            return currentOrderAsUSerFragment;
        } else {
            PreviousOrderAsUserFragment previousOrderAsUserFragment = new PreviousOrderAsUserFragment();
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