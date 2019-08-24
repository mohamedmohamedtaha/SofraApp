package com.example.sofraapp.app.data.contract;

import android.widget.CheckBox;

public interface LoginContract {

    interface ViewLogin {
        void showProgress();
        void goToMain();

        void hideProgress();

        void isEmpty();

        void showMessage(String message);



    }

    interface PresenterLogin {
        void onDestroy();

        void setView(ViewLogin viewLogin);

        void setLogin(String email, String password,
                      CheckBox LoginFragmentCBRemeberMy);

    }
}






















