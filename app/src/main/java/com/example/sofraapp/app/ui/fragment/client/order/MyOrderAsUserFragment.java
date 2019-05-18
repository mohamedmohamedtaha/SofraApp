package com.example.sofraapp.app.ui.fragment.client.order;


import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
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
public class MyOrderAsUserFragment extends Fragment {
    @BindView(R.id.MyOrderAsUSerFragment_TabLayout)
    TabLayout MyOrderAsUSerFragmentTabLayout;
    @BindView(R.id.MyOrderAsUSerFragment_ViewPager)
    ViewPager MyOrderAsUSerFragmentViewPager;
    Unbinder unbinder;
    public MyOrderAsUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order_as_u, container, false);
        unbinder = ButterKnife.bind(this, view);
        AdapterMyOrderUser adapterMyOrderUser = new AdapterMyOrderUser(getActivity(), getChildFragmentManager());
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
