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
import com.example.sofraapp.app.data.model.client.cycleClient.loginclient.LoginClient;
import com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.cyclelogin.login.Login;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.RememberMy;
import com.example.sofraapp.app.ui.activity.MainActivity;
import com.example.sofraapp.app.ui.fragment.restaurant.restaurantCycle.RegisterAsRestaurantFragmentOne;
import com.google.android.material.textfield.TextInputEditText;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofraapp.app.data.rest.RetrofitClient.getRetrofit;
import static com.example.sofraapp.app.ui.activity.MainActivity.toolbar;

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
        //for check if the user in login or not
        remeberMy = new RememberMy(getActivity());
        if (remeberMy.isRemember()) {
            HelperMethod.startActivity(getActivity(), MainActivity.class);
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
                    if (remeberMy.getSaveState() == 1) {
                        LoginFragmentProgressBar.setVisibility(View.VISIBLE);
                        Call<LoginClient> loginCall = APIServices.getLogin(email, password);
                        loginCall.enqueue(new Callback<LoginClient>() {
                            @Override
                            public void onResponse(Call<LoginClient> call, Response<LoginClient> response) {
                                try {
                                    LoginClient loginClient = response.body();
                                    if (loginClient.getStatus() == 1) {
                                        remeberMy.saveDateUserTwo(loginClient.getData().getClient().getName(),
                                                loginClient.getData().getClient().getPhone(),
                                                loginClient.getData().getClient().getEmail(),
                                                loginClient.getData().getApiToken());

                                        if (LoginFragmentCBRemeberMy.isChecked()) {
                                            remeberMy.saveDateUser(email, password, loginClient.getData().getApiToken());
                                        }
                                        HelperMethod.startActivity(getActivity(), MainActivity.class);
                                        Toast.makeText(getActivity(), loginClient.getMsg(), Toast.LENGTH_LONG).show();
                                        LoginFragmentProgressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(getActivity(), loginClient.getMsg(), Toast.LENGTH_LONG).show();
                                        LoginFragmentProgressBar.setVisibility(View.GONE);
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    LoginFragmentProgressBar.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginClient> call, Throwable t) {
                                LoginFragmentProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        LoginFragmentProgressBar.setVisibility(View.VISIBLE);
                        Call<Login> loginCall = APIServices.getLoginRestaurant(email, password);
                        loginCall.enqueue(new Callback<Login>() {
                            @Override
                            public void onResponse(Call<Login> call, Response<Login> response) {
                                try {
                                    Login loginRestaurant = response.body();
                                    if (loginRestaurant.getStatus() == 1) {
                                        remeberMy.saveDateUserTwo(loginRestaurant.getData().getUser().getName(),
                                                loginRestaurant.getData().getUser().getPhone(),
                                                loginRestaurant.getData().getUser().getEmail(),
                                                loginRestaurant.getData().getApiToken());
                                        if (LoginFragmentCBRemeberMy.isChecked()) {
                                            remeberMy.saveDateUser(email, password, loginRestaurant.getData().getApiToken());
                                        }
                                        HelperMethod.startActivity(getActivity(), MainActivity.class);
                                        Toast.makeText(getActivity(), loginRestaurant.getMsg(), Toast.LENGTH_LONG).show();
                                        LoginFragmentProgressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(getActivity(), loginRestaurant.getMsg(), Toast.LENGTH_LONG).show();
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
                }
                break;
            case R.id.LoginFragment_TV_Forget_Password:
                if (remeberMy.getSaveState() == 1) {
                    ForgetPasswordStep1Fragment forgetPasswordStep1Fragment = new ForgetPasswordStep1Fragment();
                    HelperMethod.replece(forgetPasswordStep1Fragment, getActivity().getSupportFragmentManager(),
                            R.id.Cycle_Home_contener, toolbar, getString(R.string.forget_my_password));
                } else if (remeberMy.getSaveState() == 2){

                }else {
                    Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.LoginFragment_Cretae_New_User:
                if (remeberMy.getSaveState() == 1) {
                    RegisterAsUserFragment regesterFragment = new RegisterAsUserFragment();
                    HelperMethod.replece(regesterFragment, getActivity().getSupportFragmentManager(),
                            R.id.Cycle_Home_contener, toolbar, getString(R.string.create_new_user));
                } else if (remeberMy.getSaveState() == 2) {
                    RegisterAsRestaurantFragmentOne registerAsRestaurantFragmentOne = new RegisterAsRestaurantFragmentOne();
                    HelperMethod.replece(registerAsRestaurantFragmentOne, getActivity().getSupportFragmentManager(),
                            R.id.Cycle_Home_contener, toolbar, getString(R.string.create_new_user));
                } else {
                    Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
