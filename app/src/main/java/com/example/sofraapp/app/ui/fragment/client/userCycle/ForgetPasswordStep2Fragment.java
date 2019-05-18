package com.example.sofraapp.app.ui.fragment.client.userCycle;


import android.os.Bundle;
import android.os.CountDownTimer;

import com.example.sofraapp.app.helper.RememberMy;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.client.cycleClient.forgetpassword.newpassword.NewPassword;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.SaveData;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPasswordStep2Fragment extends Fragment {
    @BindView(R.id.TV_Remind_Time)
    TextView TVRemindTime;
    @BindView(R.id.ForgetPasswordStep2Fragment_Code)
    TextInputEditText ForgetPasswordStep2FragmentCode;
    @BindView(R.id.ForgetPasswordStep2Fragment_New_Password)
    TextInputEditText ForgetPasswordStep2FragmentNewPassword;
    @BindView(R.id.ForgetPasswordStep2Fragment_Retry_Password)
    TextInputEditText ForgetPasswordStep2FragmentRetryPassword;
    @BindView(R.id.ForgetPasswordStep2Fragment_Progress_Bar)
    ProgressBar ForgetPasswordStep2FragmentProgressBar;
    @BindView(R.id.ForgetPasswordStep2Fragment_Change)
    Button ForgetPasswordStep2FragmentChange;
    Unbinder unbinder;
    private APIServices APIServices;
    boolean check_network;
    RememberMy rememberMy;
    public ForgetPasswordStep2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password_step2, container, false);
        unbinder = ButterKnife.bind(this, view);
        rememberMy = new RememberMy(getActivity());
        APIServices = getRetrofit().create(APIServices.class);
        // for check network
        check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == true) {
            HelperMethod.startCountdownTimer(getActivity(), getActivity().findViewById(android.R.id.content)
                    , getActivity().getSupportFragmentManager(), TVRemindTime, null);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ForgetPasswordStep2Fragment_Change)
    public void onViewClicked() {
        // for check network
        if (check_network == false) {
            return;
        }
        String code = ForgetPasswordStep2FragmentCode.getText().toString().trim();
        String newPassword = ForgetPasswordStep2FragmentNewPassword.getText().toString().trim();
        String retryNewPassword = ForgetPasswordStep2FragmentRetryPassword.getText().toString().trim();
        if (code.isEmpty() || newPassword.isEmpty() || retryNewPassword.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.filed_request), Toast.LENGTH_LONG).show();
            return;
        }
        ForgetPasswordStep2FragmentProgressBar.setVisibility(View.VISIBLE);
        if (rememberMy.getSaveState() == 1){
             APIServices.getNewPassword(code, newPassword, retryNewPassword)
            .enqueue(new Callback<NewPassword>() {
                @Override
                public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
                    NewPassword newPassword1 = response.body();
                    if (newPassword1.getStatus() == 1) {
                        HelperMethod.stopCountdownTimer();
                        ForgetPasswordStep2FragmentProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), newPassword1.getMsg(), Toast.LENGTH_SHORT).show();
                        LoginFragment loginFragment = new LoginFragment();
                        HelperMethod.replece(loginFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.login));
                    } else {
                        ForgetPasswordStep2FragmentProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), newPassword1.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<NewPassword> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    ForgetPasswordStep2FragmentProgressBar.setVisibility(View.GONE);
                }
            });
        }else if (rememberMy.getSaveState() == 2){
            APIServices.getNewPasswordRestaurant(code, newPassword, retryNewPassword).
                    enqueue(new Callback<com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.forgetpassword.newpassword.NewPassword>() {
                @Override
                public void onResponse(Call<com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.forgetpassword.newpassword.NewPassword> call, Response<com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.forgetpassword.newpassword.NewPassword> response) {
                    com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.forgetpassword.newpassword.NewPassword newPassword1 = response.body();
                    if (newPassword1.getStatus() == 1) {
                        HelperMethod.stopCountdownTimer();
                        ForgetPasswordStep2FragmentProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), newPassword1.getMsg(), Toast.LENGTH_SHORT).show();
                        LoginFragment loginFragment = new LoginFragment();
                        HelperMethod.replece(loginFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.login));
                    } else {
                        ForgetPasswordStep2FragmentProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), newPassword1.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.forgetpassword.newpassword.NewPassword> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    ForgetPasswordStep2FragmentProgressBar.setVisibility(View.GONE);
                }
            });
        }else {
            Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
        }

    }
}
