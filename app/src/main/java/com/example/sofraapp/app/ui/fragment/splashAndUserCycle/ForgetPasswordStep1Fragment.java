package com.example.sofraapp.app.ui.fragment.splashAndUserCycle;


import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.cycleClient.resetpassword.ResetPassword;
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
public class ForgetPasswordStep1Fragment extends Fragment {
    @BindView(R.id.ForgetPasswordStep1Fragment_Email)
    TextInputEditText ForgetPasswordStep1FragmentEmail;
    @BindView(R.id.ForgetPasswordStep1Fragment_Progress_Bar)
    ProgressBar ForgetPasswordStep1FragmentProgressBar;
    @BindView(R.id.ForgetPasswordStep1Fragment_BT_Login)
    Button ForgetPasswordStep1FragmentBTLogin;
    Unbinder unbinder;
    SaveData saveData;
    public ForgetPasswordStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password_step1, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ForgetPasswordStep1Fragment_BT_Login)
    public void onViewClicked() {
        // for check network
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
            return;
        }
        final String email = ForgetPasswordStep1FragmentEmail.getText().toString().trim();
        if (!email.isEmpty()) {
            ForgetPasswordStep1FragmentProgressBar.setVisibility(View.VISIBLE);
            APIServices apiServices = getRetrofit().create(APIServices.class);
            Call<ResetPassword> resetPassword = apiServices.getResetPassword(email);
            resetPassword.enqueue(new Callback<ResetPassword>() {
                @Override
                public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                    ResetPassword resetPassword1 = response.body();
                    //  bundle.putString(PHONE, email);
                    if (resetPassword1.getStatus() == 1) {
                        ForgetPasswordStep2Fragment forgetPasswordStep2Fragment = new ForgetPasswordStep2Fragment();
                        HelperMethod.replece(forgetPasswordStep2Fragment, getActivity().getSupportFragmentManager(),
                                R.id.Cycle_Home_contener, toolbar, getString(R.string.forget_my_password_l), saveData);
                        Toast.makeText(getActivity(), resetPassword1.getMsg(), Toast.LENGTH_LONG).show();
                        ForgetPasswordStep1FragmentProgressBar.setVisibility(View.GONE);
                    } else {
                        ForgetPasswordStep1FragmentProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), resetPassword1.getMsg(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResetPassword> call, Throwable t) {
                    ForgetPasswordStep1FragmentProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            ForgetPasswordStep1FragmentEmail.setError(getString(R.string.filed_request));
        }
    }
}
