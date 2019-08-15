package com.example.sofraapp.app.helper;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    RememberMy rememberMy;
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
//        rememberMy = new RememberMy(getApplicationContext());
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
  //      HelperMethod.getRemoveToken(getApplicationContext(),refreshedToken,rememberMy.getAPIKey());

    }
}
