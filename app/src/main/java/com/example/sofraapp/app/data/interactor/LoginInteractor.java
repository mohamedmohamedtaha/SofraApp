package com.example.sofraapp.app.data.interactor;

import android.content.Context;
import android.util.Log;
import android.widget.CheckBox;

import com.example.sofraapp.app.data.contract.LoginContract;
import com.example.sofraapp.app.data.model.client.cycleClient.loginclient.LoginClient;
import com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.cyclelogin.login.Login;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.library.dagger.scope.ApiScope;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.RememberMy;
import com.example.sofraapp.app.helper.library.dagger.daggerApp.MyApp;
import com.google.firebase.iid.FirebaseInstanceId;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@ApiScope
public class LoginInteractor implements LoginContract.PresenterLogin {
    private LoginContract.ViewLogin viewLogin;
    private RememberMy rememberMy;
    private Context context;
    @Inject
     APIServices APIServices;

    public LoginInteractor(Context context) {
        this.context = context;
        rememberMy = new RememberMy(context);
       // ((MyApp)context.getApplicationContext()).getLApiComponent().inject(this);
       // MyApp.getLApiComponent().inject(this);
        MyApp.getInstance().getLApiComponent().inject(this);


    }

    @Override
    public void onDestroy() {
        viewLogin = null;
    }

    @Override
    public void setView(LoginContract.ViewLogin viewLogin) {
        this.viewLogin = viewLogin;

    }

    @Override
    public void setLogin( String email, String password
            , CheckBox LoginFragmentCBRemeberMy) {

        if (email.isEmpty() || password.isEmpty()) {
            viewLogin.isEmpty();
            return;
        } else {
            if (rememberMy.getSaveState() == 1) {
                viewLogin.showProgress();
                Call<LoginClient> loginCall = APIServices.getLogin(email, password);
                loginCall.enqueue(new Callback<LoginClient>() {
                    @Override
                    public void onResponse(Call<LoginClient> call, Response<LoginClient> response) {
                        try {
                            LoginClient loginClient = response.body();
                            if (loginClient.getStatus() == 1) {
                                rememberMy.saveDateUserTwo(loginClient.getData().getClient().getName(),
                                        loginClient.getData().getClient().getPhone(),
                                        loginClient.getData().getClient().getEmail(),
                                        loginClient.getData().getClient().getAddress(),
                                        loginClient.getData().getApiToken(), loginClient.getData().getClient().getProfilePath(), password);
                                String token = FirebaseInstanceId.getInstance().getToken();
                                HelperMethod.getRegisterToken(context, token, loginClient.getData().getApiToken(),
                                        "android", 1);
                                Log.i("API Key : ", loginClient.getData().getApiToken());

                                if (LoginFragmentCBRemeberMy.isChecked()) {
                                    rememberMy.saveDateUser(email, password, loginClient.getData().getApiToken());
                                }
                                viewLogin.goToMain();
                                viewLogin.showMessage(loginClient.getMsg());
                                viewLogin.hideProgress();
                            } else {
                                viewLogin.hideProgress();
                                viewLogin.showMessage(loginClient.getMsg());
                            }

                        } catch (Exception e) {
                            viewLogin.hideProgress();
                            viewLogin.showMessage(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginClient> call, Throwable t) {
                        viewLogin.hideProgress();
                        viewLogin.showMessage(t.getMessage());
                    }
                });
            } else {
                viewLogin.showProgress();
                Call<Login> loginCall = APIServices.getLoginRestaurant(email, password);
                loginCall.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        try {
                            Login loginRestaurant = response.body();
                            if (loginRestaurant.getStatus() == 1) {
                                rememberMy.saveDateUserTwo(loginRestaurant.getData().getUser().getName(),
                                        loginRestaurant.getData().getUser().getPhone(),
                                        loginRestaurant.getData().getUser().getEmail(),
                                        loginRestaurant.getData().getUser().getActivated(),
                                        loginRestaurant.getData().getApiToken(),
                                        loginRestaurant.getData().getUser().getPhotoUrl(), password);
                                Log.i("API Key : ", loginRestaurant.getData().getApiToken());

                                String token = FirebaseInstanceId.getInstance().getToken();
                                HelperMethod.getRegisterToken(context, token, loginRestaurant.getData().getApiToken(),
                                        "android", 2);
                                if (LoginFragmentCBRemeberMy.isChecked()) {
                                    rememberMy.saveDateUser(email, password, loginRestaurant.getData().getApiToken());
                                }
                                viewLogin.goToMain();
                                viewLogin.hideProgress();
                                viewLogin.showMessage(loginRestaurant.getMsg());
                            } else {
                                viewLogin.hideProgress();
                                viewLogin.showMessage(loginRestaurant.getMsg());
                            }

                        } catch (Exception e) {
                            viewLogin.hideProgress();
                            viewLogin.showMessage(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        viewLogin.hideProgress();
                        viewLogin.showMessage(t.getMessage());
                    }
                });
            }
        }

    }
}
