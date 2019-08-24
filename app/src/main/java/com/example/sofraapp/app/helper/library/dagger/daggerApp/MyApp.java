package com.example.sofraapp.app.helper.library.dagger.daggerApp;

import android.app.Application;
import com.example.sofraapp.app.helper.library.dagger.daggerApi.ApiComponent;
import com.example.sofraapp.app.helper.library.dagger.daggerApi.ApiModule;
import com.example.sofraapp.app.helper.library.dagger.daggerLogin.LoginComponent;
import com.example.sofraapp.app.helper.library.dagger.daggerLogin.LoginModule;

public class MyApp extends Application{
    protected static MyApp instance;
    public static MyApp getInstance(){
        return instance;
    }
    private LoginComponent loginComponent;
    private AppComponent appComponent;
    private static ApiComponent apiComponent;

    public LoginComponent getLoginComponent(){
        if (loginComponent == null){
            loginComponent = appComponent.loginComponent(new LoginModule());
        }
        return loginComponent;
    }

    public  ApiComponent getLApiComponent(){
        if (apiComponent == null){
            apiComponent = appComponent.apiComponent(new ApiModule());

        }
        return apiComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(instance)).build();


    }
}
