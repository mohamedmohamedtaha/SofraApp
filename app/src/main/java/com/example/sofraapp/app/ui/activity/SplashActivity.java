package com.example.sofraapp.app.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.SaveData;
import com.example.sofraapp.app.ui.fragment.SplashFragment;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private Boolean exitApp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SplashFragment splashFragment = new SplashFragment();
        HelperMethod.replece(splashFragment, getSupportFragmentManager(), R.id.Cycle_Splash_Login_contener, null, null);
    }

    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            if (exitApp) {
                HelperMethod.closeApp(getApplicationContext());
                return;
            }
            exitApp = true;
            Toast.makeText(this, getString(R.string.exit_app), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exitApp = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }
    }
}