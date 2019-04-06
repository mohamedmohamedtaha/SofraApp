package com.example.sofraapp.app.helper;

import android.content.Context;
import android.content.SharedPreferences;

import static com.example.sofraapp.app.helper.HelperMethod.API_KEY;

public class RememberMy {
    public static final String FOLDERNAME = "saveUser";
    public static final String KEY_REMEMBERMY = "rememberMy";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // This is constructor
    public RememberMy(Context context) {
        sharedPreferences = context.getSharedPreferences(FOLDERNAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void removeDateUser(Context context){
        sharedPreferences = context.getSharedPreferences(FOLDERNAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    // This method for save data User
    public void saveDateUser(String email, String password,String getAPI_key) {
        editor.putBoolean(KEY_REMEMBERMY, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(API_KEY, getAPI_key);

        editor.commit();

    }
    public String getDataUser(Context context){
        sharedPreferences = context.getSharedPreferences(FOLDERNAME, Context.MODE_PRIVATE);
        String vaue = sharedPreferences.getString(API_KEY,null);
        return vaue;
    }

    // This method for check Do the user save data or not ?
    public boolean isRemember() {
        if (sharedPreferences.getBoolean(KEY_REMEMBERMY, false)) {
            return true;
        } else {
            return false;
        }
    }

}
