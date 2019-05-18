package com.example.sofraapp.app.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sofraapp.R;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.RememberMy;
import com.example.sofraapp.app.ui.activity.MainActivity;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {
    @BindView(R.id.Order_Food)
    Button OrderFood;
    @BindView(R.id.Sell_Food)
    Button SellFood;
    Unbinder unbinder;
    RememberMy rememberMy;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        unbinder = ButterKnife.bind(this, view);
        rememberMy = new RememberMy(getActivity());
        if (rememberMy.isRemember()) {
            HelperMethod.startActivity(getActivity(), MainActivity.class);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Order_Food, R.id.Sell_Food})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Order_Food:
                rememberMy.setSaveState(1);
                HelperMethod.startActivity(getActivity(), MainActivity.class);
                break;
            case R.id.Sell_Food:
                rememberMy.setSaveState(2);
                HelperMethod.startActivity(getActivity(), MainActivity.class);
                break;
        }
    }
}
