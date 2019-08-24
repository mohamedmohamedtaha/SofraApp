package com.example.sofraapp.app.helper.library.dagger.daggerLogin;

import android.content.Context;

import com.example.sofraapp.app.data.contract.LoginContract;
import com.example.sofraapp.app.data.interactor.LoginInteractor;
import com.example.sofraapp.app.helper.library.dagger.scope.ApiScope;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {
    @Provides
    @ApiScope
    public LoginContract.PresenterLogin provideLoginContractPresenterLogin(Context context) {
        return new LoginInteractor(context);
    }
}
