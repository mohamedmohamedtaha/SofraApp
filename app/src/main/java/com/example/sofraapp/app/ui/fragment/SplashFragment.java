package com.example.sofraapp.app.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.RememberMy;
import com.example.sofraapp.app.helper.SaveData;
import com.example.sofraapp.app.ui.activity.MainActivity;
import com.example.sofraapp.app.ui.fragment.mainCycle.OffersFragment;

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
    private static final long SPLASH_DISPAY_LENGTH = 3000;
    RememberMy rememberMy;
    String getAPI_key;
    public static int SAVE_STATE = 0;
    SaveData saveData ;


    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        rememberMy = new RememberMy(getActivity());
/*

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (rememberMy.isRemember()){
                    getAPI_key=rememberMy.getDataUser(getActivity());
                    HelperMethod.startActivity(getActivity(), MainActivity.class,getAPI_key);

                }else {
                    LoginFragment loginFragment = new LoginFragment();
                    HelperMethod.replece(loginFragment, getActivity().getSupportFragmentManager(),
                            R.id.Cycle_Splash_Login_contener, null, null, null);

                }


            }
        }, SPLASH_DISPAY_LENGTH);*/
        unbinder = ButterKnife.bind(this, view);
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
                SAVE_STATE = 1 ;
                saveData = new SaveData(SAVE_STATE,0);
                HelperMethod.startActivity(getActivity(), MainActivity.class,saveData);
                break;
            case R.id.Sell_Food:
                SAVE_STATE = 2 ;
                saveData = new SaveData(SAVE_STATE,0);
                HelperMethod.startActivity(getActivity(), MainActivity.class,saveData);
                break;
        }
    }
}
