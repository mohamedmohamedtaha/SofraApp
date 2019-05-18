package com.example.sofraapp.app.ui.fragment.restaurant.orders;


import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.AdapterOrdersAsRestaurant;
import com.example.sofraapp.app.helper.SaveData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.sofraapp.app.helper.HelperMethod.GET_DATA;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersAsRestaurantFragment extends Fragment {
   @BindView(R.id.OrdersAsRestaurantFragment_TabLayout)
    TabLayout OrdersAsRestaurantFragmentTabLayout;
    @BindView(R.id.OrdersAsRestaurantFragment_ViewPager)
    ViewPager OrdersAsRestaurantFragmentViewPager;
    Unbinder unbinder;

    public OrdersAsRestaurantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        unbinder = ButterKnife.bind(this, view);
        AdapterOrdersAsRestaurant adapterOrdersAsRestaurant = new AdapterOrdersAsRestaurant(getChildFragmentManager(),
                getActivity(),null);
        OrdersAsRestaurantFragmentViewPager.setAdapter(adapterOrdersAsRestaurant);
        OrdersAsRestaurantFragmentTabLayout.setupWithViewPager(OrdersAsRestaurantFragmentViewPager);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
