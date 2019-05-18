package com.example.sofraapp.app.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sofraapp.R;
import com.example.sofraapp.app.helper.SaveData;
import com.example.sofraapp.app.ui.fragment.restaurant.orders.CurrentOrdersAsRestaurantFragment;
import com.example.sofraapp.app.ui.fragment.restaurant.orders.NewOrdersAsRestaurantFragment;
import com.example.sofraapp.app.ui.fragment.restaurant.orders.PreviousOrdersAsRestaurantFragment;

import static com.example.sofraapp.app.helper.HelperMethod.GET_DATA;
public class AdapterOrdersAsRestaurant extends FragmentPagerAdapter {
    private Context context;
    private SaveData saveData;
    public AdapterOrdersAsRestaurant(FragmentManager fm, Context context, SaveData saveData) {
        super(fm);
        this.context = context;
        this.saveData = saveData;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            NewOrdersAsRestaurantFragment newOrdersAsRestaurantFragment = new NewOrdersAsRestaurantFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(GET_DATA, saveData);
            newOrdersAsRestaurantFragment.setArguments(bundle);
            return newOrdersAsRestaurantFragment;
        } else if (position == 1) {
            CurrentOrdersAsRestaurantFragment currentOrdersAsRestaurantFragment = new CurrentOrdersAsRestaurantFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(GET_DATA, saveData);
            currentOrdersAsRestaurantFragment.setArguments(bundle);
            return currentOrdersAsRestaurantFragment;
        } else {
            PreviousOrdersAsRestaurantFragment previousOrdersAsRestaurantFragment = new PreviousOrdersAsRestaurantFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(GET_DATA, saveData);
            previousOrdersAsRestaurantFragment.setArguments(bundle);
            return previousOrdersAsRestaurantFragment;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.new_order);
        } else if (position == 1) {
            return context.getString(R.string.current_order);
        } else {
            return context.getString(R.string.previus_order);
        }
    }
}
