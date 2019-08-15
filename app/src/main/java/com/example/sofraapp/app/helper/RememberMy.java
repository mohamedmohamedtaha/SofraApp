package com.example.sofraapp.app.helper;

import android.content.Context;
import android.content.SharedPreferences;

import static com.example.sofraapp.app.helper.HelperMethod.API_KEY;

public class RememberMy {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String FOLDERNAME = "saveUser";
    public static final String KEY_REMEMBERMY = "rememberMy";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String SAVE_STATE = "save_state";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ADDRESSS = "address";
    public static final String KEY_PROFILE = "profile_path";



    // This is constructor
    public RememberMy(Context context) {
        sharedPreferences = context.getSharedPreferences(FOLDERNAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void removeDateUser(Context context) {
        sharedPreferences = context.getSharedPreferences(FOLDERNAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    // This method for save state User
    public void setSaveState(int save_state) {
        editor.putInt(SAVE_STATE, save_state);
        editor.commit();
    }

    // This method for get  state User
    public int getSaveState() {
        int saveState = sharedPreferences.getInt(SAVE_STATE, 0);
        return saveState;
    }

    // This method for save State User
    public void saveDateUser(String email, String password, String getAPI_key) {
        editor.putBoolean(KEY_REMEMBERMY, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(API_KEY, getAPI_key);
        editor.commit();
    }

    // This method for save Date User Two
    public void saveDateUserTwo(String name, String phone,String email,String Address,String getAPI_key,String profilePath,
                                String password) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PHONE, phone);
        editor.putString(API_KEY, getAPI_key);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ADDRESSS, Address);
        editor.putString(KEY_PROFILE,profilePath);
        editor.putString(KEY_PASSWORD,password);

        editor.commit();
    }
    //This method for getProfilePath
    public String getPassword() {
        String save_password = sharedPreferences.getString(KEY_PASSWORD, null);
        return save_password;
    }
    //This method for getProfilePath
    public String getProfilePath() {
        String save_path = sharedPreferences.getString(KEY_PROFILE, null);
        return save_path;
    }
    // This method for getNameUser
    public String getNameUser() {
        String saveName = sharedPreferences.getString(KEY_NAME, null);
        return saveName;
    }
    // This method for getAddress
    public String getAddress() {
        String saveAddress = sharedPreferences.getString(KEY_ADDRESSS, null);
        return saveAddress;
    }
    // This method for getEmail
    public String getEmailUser() {
        String saveEmail = sharedPreferences.getString(KEY_EMAIL, null);
        return saveEmail;
    }
    // This method for getPhoneUser
    public String getPhoneUser() {
        String savePhone = sharedPreferences.getString(KEY_PHONE, null);
        return savePhone;
    }
    public String getAPIKey() {
        String vaue = sharedPreferences.getString(API_KEY, null);
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
