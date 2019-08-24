package com.example.sofraapp.app.data.rest;

import android.content.Context;
import android.net.ConnectivityManager;
import dagger.Module;
import dagger.Provides;

@Module
public class AppNetworkModule {

    @Provides
    public static ConnectivityManager ProvideConnectivityManager(Context context) {
        return  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

    }
}
