package com.example.sofraapp.app.ui.fragment.restaurant.restaurantCycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.forgetpassword.resetpassword.ResetPassword;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.RememberMy;
import com.example.sofraapp.app.ui.fragment.client.userCycle.ForgetPasswordStep2Fragment;
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
public class ForgetPasswordAsRestaurantStep1Fragment extends Fragment {
    @BindView(R.id.ForgetPasswordAsRestaurantStep1Fragment_Email)
    TextInputEditText ForgetPasswordAsRestaurantStep1FragmentEmail;
    @BindView(R.id.ForgetPasswordAsRestaurantStep1Fragment_Password)
    TextInputEditText ForgetPasswordAsRestaurantStep1FragmentPassword;
    @BindView(R.id.ForgetPasswordAsRestaurantStep1Fragment_Password_confirmation)
    TextInputEditText ForgetPasswordAsRestaurantStep1FragmentPasswordConfirmation;
    @BindView(R.id.ForgetPasswordAsRestaurantStep1Fragment_Progress_Bar)
    ProgressBar ForgetPasswordAsRestaurantStep1FragmentProgressBar;
    @BindView(R.id.ForgetPasswordAsRestaurantStep1Fragment_BT_Login)
    Button ForgetPasswordAsRestaurantStep1FragmentBTLogin;
    Unbinder unbinder;
    RememberMy rememberMy;
    APIServices apiServices;


    public ForgetPasswordAsRestaurantStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password_as_restaurant_step1, container, false);
        unbinder = ButterKnife.bind(this, view);
        rememberMy = new RememberMy(getActivity());
        apiServices = getRetrofit().create(APIServices.class);


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.ForgetPasswordAsRestaurantStep1Fragment_BT_Login)
    public void onViewClicked() {
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
            return;
        }
        String email = ForgetPasswordAsRestaurantStep1FragmentEmail.getText().toString().trim();
        String password = ForgetPasswordAsRestaurantStep1FragmentPassword.getText().toString().trim();
        String password_confirmation = ForgetPasswordAsRestaurantStep1FragmentPasswordConfirmation.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty() || password_confirmation.isEmpty()) {
            Toast.makeText(getActivity(), R.string.filed_request, Toast.LENGTH_SHORT).show();
            return;
        }
        ForgetPasswordAsRestaurantStep1FragmentProgressBar.setVisibility(View.VISIBLE);
        apiServices.getResetPasswordRestaurant(email, password, password_confirmation).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                ResetPassword resetPassword = response.body();
                try {
                    if (resetPassword.getStatus() == 1) {
                        ForgetPasswordStep2Fragment forgetPasswordStep2Fragment = new ForgetPasswordStep2Fragment();
                        HelperMethod.replece(forgetPasswordStep2Fragment, getActivity().getSupportFragmentManager(),
                                R.id.Cycle_Home_contener, toolbar, getString(R.string.forget_my_password_l));
                        ForgetPasswordAsRestaurantStep1FragmentProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), resetPassword.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), resetPassword.getMsg(), Toast.LENGTH_SHORT).show();
                        ForgetPasswordAsRestaurantStep1FragmentProgressBar.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    ForgetPasswordAsRestaurantStep1FragmentProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                ForgetPasswordAsRestaurantStep1FragmentProgressBar.setVisibility(View.GONE);
            }
        });

    }
}
