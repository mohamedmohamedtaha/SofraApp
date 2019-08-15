package com.example.sofraapp.app.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.ui.fragment.client.userCycle.LoginFragment;

public class LoginActivity extends AppCompatActivity {
    private Boolean exitApp = false;
    public static Toolbar toolbar_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar_Login = (Toolbar) findViewById(R.id.Toolbar_Login);
        setSupportActionBar(toolbar_Login);
        LoginFragment loginFragment = new LoginFragment();
        HelperMethod.replece(loginFragment, getSupportFragmentManager(), R.id.Cycle_Login_contener, toolbar_Login, getString(R.string.login));

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
