package com.example.sofraapp.app.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.AdapterMyOrderUser;
import com.example.sofraapp.app.helper.SaveData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.sofraapp.app.helper.HelperMethod.GET_DATA;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderAsUSerFragment extends Fragment {


    @BindView(R.id.MyOrderAsUSerFragment_TabLayout)
    TabLayout MyOrderAsUSerFragmentTabLayout;
    @BindView(R.id.MyOrderAsUSerFragment_ViewPager)
    ViewPager MyOrderAsUSerFragmentViewPager;
    Unbinder unbinder;
    SaveData saveData;

    public MyOrderAsUSerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order_as_u, container, false);
        unbinder = ButterKnife.bind(this, view);


        saveData = getArguments().getParcelable(GET_DATA);
        AdapterMyOrderUser adapterMyOrderUser = new AdapterMyOrderUser(getActivity(), getChildFragmentManager(), saveData);
        MyOrderAsUSerFragmentViewPager.setAdapter(adapterMyOrderUser);
        MyOrderAsUSerFragmentTabLayout.setupWithViewPager(MyOrderAsUSerFragmentViewPager);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
