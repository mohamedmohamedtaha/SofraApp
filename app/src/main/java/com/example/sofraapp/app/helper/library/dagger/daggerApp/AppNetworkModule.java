package com.example.sofraapp.app.helper.library.dagger.daggerApp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import dagger.Module;
import dagger.Provides;

@Module
public class AppNetworkModule {

    @Provides
    public static ConnectivityManager ProvideConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    }
/*
    @Provides
    public static NetworkInfo provideNetworkInfo(ConnectivityManager connectivityManager) {
        return connectivityManager.getActiveNetworkInfo();
    }*/

}
