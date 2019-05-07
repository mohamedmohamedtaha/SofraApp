package com.example.sofraapp.app.ui.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sofraapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartOrdersFragment extends Fragment {


    public CartOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart_orders, container, false);
    }

}
