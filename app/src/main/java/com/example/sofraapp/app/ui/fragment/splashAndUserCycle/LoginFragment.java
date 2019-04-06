package com.example.sofraapp.app.ui.fragment.splashAndUserCycle;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.cycleClient.login.Login;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.RememberMy;
import com.example.sofraapp.app.helper.SaveData;
import com.example.sofraapp.app.ui.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofraapp.app.data.rest.RetrofitClient.getRetrofit;
import static com.example.sofraapp.app.helper.HelperMethod.GET_DATA;
import static com.example.sofraapp.app.ui.activity.MainActivity.toolbar;
import static com.example.sofraapp.app.ui.fragment.SplashFragment.SAVE_STATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
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
    Unbinder unbinder;
    private static APIServices APIServices;

    RememberMy remeberMy;
    String getAPI_key;
    private SaveData saveSate;
    String email;
    String password;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveSate = getArguments().getParcelable(GET_DATA);
        //for check if the user in login or not
        remeberMy = new RememberMy(getActivity());
        if (remeberMy.isRemember()) {
            HelperMethod.startActivity(getActivity(), MainActivity.class, getAPI_key);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                APIServices = getRetrofit().create(APIServices.class);
                if (email.isEmpty() || password.isEmpty()) {
                    LoginFragmentEmail.setError(getString(R.string.filed_request));
                    return;
                } else {
                    LoginFragmentProgressBar.setVisibility(View.VISIBLE);
                    Call<Login> loginCall = APIServices.getLogin(email, password);
                    loginCall.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            try {
                                Login login = response.body();
                                if (login.getStatus() == 1) {
                                    SaveData saveData = new SaveData(login.getData().getUser().getId(), login.getData().getApiToken(),
                                            login.getData().getUser().getName(), login.getData().getUser().getPhone()
                                            , login.getData().getUser().getEmail()
                                            , login.getData().getUser().getRegion().getId()
                                            , login.getData().getUser().getRegion().getCityId()
                                            , login.getData().getUser().getAddress(),SAVE_STATE);
                                    if (LoginFragmentCBRemeberMy.isChecked()) {
                                        remeberMy.saveDateUser(email, password, getAPI_key);
                                    }
                                    HelperMethod.startActivity(getActivity(), MainActivity.class, saveData);
                                    Toast.makeText(getActivity(), login.getMsg(), Toast.LENGTH_LONG).show();
                                    LoginFragmentProgressBar.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(getActivity(), login.getMsg(), Toast.LENGTH_LONG).show();
                                    LoginFragmentProgressBar.setVisibility(View.GONE);
                                }

                            } catch (Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                LoginFragmentProgressBar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            LoginFragmentProgressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
                break;
            case R.id.LoginFragment_TV_Forget_Password:
                ForgetPasswordStep1Fragment forgetPasswordStep1Fragment = new ForgetPasswordStep1Fragment();
                HelperMethod.replece(forgetPasswordStep1Fragment, getActivity().getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, toolbar, getString(R.string.forget_my_password), saveSate);
                break;
            case R.id.LoginFragment_Cretae_New_User:
                RegisterAsUserFragment regesterFragment = new RegisterAsUserFragment();
                HelperMethod.replece(regesterFragment, getActivity().getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, toolbar, getString(R.string.create_new_user), saveSate);
                break;
        }
    }
}
