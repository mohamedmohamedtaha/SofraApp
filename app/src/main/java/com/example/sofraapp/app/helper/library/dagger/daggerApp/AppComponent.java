package com.example.sofraapp.app.helper.library.dagger.daggerApp;

import com.example.sofraapp.app.helper.library.dagger.daggerApi.ApiComponent;
import com.example.sofraapp.app.helper.library.dagger.daggerApi.ApiModule;
import com.example.sofraapp.app.helper.library.dagger.daggerLogin.LoginComponent;
import com.example.sofraapp.app.helper.library.dagger.daggerLogin.LoginModule;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    ApiComponent apiComponent(ApiModule apiModule);
    LoginComponent loginComponent(LoginModule loginModule);
}

