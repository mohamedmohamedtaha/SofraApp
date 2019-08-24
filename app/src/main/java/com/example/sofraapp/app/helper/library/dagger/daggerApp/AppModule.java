package com.example.sofraapp.app.helper.library.dagger.daggerApp;

import android.content.Context;

import com.example.sofraapp.app.helper.library.dagger.scope.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private  Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    /**
     * Allow the application context to be injected but require that it be annotated with
     * {@link ForApplication @Annotation} to explicitly differentiate it from an activity context.
     */
    @Provides
    @Singleton
    public Context provideApplicationContext(){
        return context;
    }
}
