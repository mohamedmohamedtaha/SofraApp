package com.example.sofraapp.app.data.contract;

import android.widget.CheckBox;

public interface LoginContract {

    interface ViewLogin {
        void showProgress();

        void hideProgress();

        void showError(String message);

        void isEmpty();

        void showMessage(String message);

        void allFieldRequered();


    }

    interface PresenterLogin {
        void onDestroy();

        void setView(ViewLogin viewLogin);

        void setLogin(String email, String password,
                      CheckBox LoginFragmentCBRemeberMy);

    }
}






















