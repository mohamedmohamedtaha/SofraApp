package com.example.sofraapp.app.ui.fragment.client.userCycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.contract.LoginContract;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.RememberMy;
import com.example.sofraapp.app.helper.library.dagger.daggerApp.MyApp;
import com.example.sofraapp.app.ui.activity.MainActivity;
import com.example.sofraapp.app.ui.fragment.restaurant.restaurantCycle.ForgetPasswordAsRestaurantStep1Fragment;
import com.example.sofraapp.app.ui.fragment.restaurant.restaurantCycle.RegisterAsRestaurantFragmentOne;
import com.google.android.material.textfield.TextInputEditText;

import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.sofraapp.app.ui.activity.LoginActivity.toolbar_Login;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginContract.ViewLogin {
    @BindView(R.id.LoginFragment_Email)
    TextInputEditText LoginFragmentEmail;
    @BindView(R.id.LoginFragment_Password)
    TextInputEditText LoginFragmentPassword;
    @BindView(R.id.LoginFragment_Progress_Bar)
    ProgressBar LoginFragmentProgressBar;
    @BindView(R.id.LoginFragment_CB_Remeber_My)
    CheckBox LoginFragmentCBRemeberMy;
    @BindView(R.id.LoginFragment_BT_Login)
    Button LoginFragmentBTLogin;
    @BindView(R.id.LoginFragment_TV_Forget_Password)
    TextView LoginFragmentTVForgetPassword;
    @BindView(R.id.LoginFragment_Cretae_New_User)
    Button LoginFragmentCretaeNewUser;
    @BindString(R.string.filed_request)String required;

    Unbinder unbinder;
    private static APIServices APIServices;
    RememberMy remeberMy;
    String email;
    String password;
    @Inject
    LoginContract.PresenterLogin presenterLogin ;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        presenterLogin.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        //for check if the user in login or not
        remeberMy = new RememberMy(getActivity());
        MyApp.getInstance().getLoginComponent().inject(this);

        //((MyApp)getActivity().getApplication()).getLoginComponent().inject(this);
        if (remeberMy.isRemember()) {
            HelperMethod.startActivity(getActivity(), MainActivity.class);
        }
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenterLogin.onDestroy();
    }

    @OnClick({R.id.LoginFragment_CB_Remeber_My, R.id.LoginFragment_BT_Login, R.id.LoginFragment_TV_Forget_Password, R.id.LoginFragment_Cretae_New_User})
    public void onViewClicked(View view) {
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
            return;
        }
        switch (view.getId()) {
            case R.id.LoginFragment_BT_Login:

                email = LoginFragmentEmail.getText().toString().trim();
                password = LoginFragmentPassword.getText().toString().trim();
                presenterLogin.setLogin(email,password,LoginFragmentCBRemeberMy);
                break;
            case R.id.LoginFragment_TV_Forget_Password:
                if (remeberMy.getSaveState() == 1) {
                    ForgetPasswordStep1Fragment forgetPasswordStep1Fragment = new ForgetPasswordStep1Fragment();
                    HelperMethod.replece(forgetPasswordStep1Fragment, getActivity().getSupportFragmentManager(),
                            R.id.Cycle_Login_contener, toolbar_Login, getString(R.string.forget_my_password));
                } else if (remeberMy.getSaveState() == 2) {
                    ForgetPasswordAsRestaurantStep1Fragment forgetPasswordAsRestaurantStep1Fragment = new ForgetPasswordAsRestaurantStep1Fragment();
                    HelperMethod.replece(forgetPasswordAsRestaurantStep1Fragment, getActivity().getSupportFragmentManager(),
                            R.id.Cycle_Login_contener, toolbar_Login, getString(R.string.forget_my_password));
                }else {
                    Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.LoginFragment_Cretae_New_User:
                if (remeberMy.getSaveState() == 1) {
                    RegisterAsUserFragment regesterFragment = new RegisterAsUserFragment();
                    HelperMethod.replece(regesterFragment, getActivity().getSupportFragmentManager(),
                            R.id.Cycle_Login_contener, toolbar_Login, getString(R.string.create_new_user));
                } else if (remeberMy.getSaveState() == 2) {
                    RegisterAsRestaurantFragmentOne registerAsRestaurantFragmentOne = new RegisterAsRestaurantFragmentOne();
                    HelperMethod.replece(registerAsRestaurantFragmentOne, getActivity().getSupportFragmentManager(),
                            R.id.Cycle_Login_contener, toolbar_Login, getString(R.string.create_new_user));
                } else {
                    Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void showProgress() {
        LoginFragmentProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void goToMain() {
        HelperMethod.startActivity(getActivity(), MainActivity.class);

    }

    @Override
    public void hideProgress() {
        LoginFragmentProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void isEmpty() {
        Toast.makeText(getActivity(), required, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }

}
