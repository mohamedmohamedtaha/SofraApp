package com.example.sofraapp.app.helper.library.dagger.daggerApi;

import com.example.sofraapp.app.data.rest.APIServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    private static final String BASE_URL = "http://ipda3.com/sofra/api/v1/";
    @Provides
    static String provideBaseUrl(){
        return BASE_URL;
    }

    @Provides
    static Gson provideGson(){
        return new GsonBuilder().setLenient().create();
    }
    @Provides
    static Retrofit provideRetrofit(String baseUrl,Gson gson){
        return  new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }
    @Provides
    APIServices provideAPIServices(Retrofit retrofit){
        return retrofit.create(APIServices.class);
    }
}














